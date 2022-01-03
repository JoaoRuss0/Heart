<template>
        <b-container>
            <h1 class="mb-4 mt-4">
                Update Observacao '{{ this.observacao.id }}'
            </h1>
            <b-form @submit.prevent="update" :disabled="!isFormValid">

                <!-- Email Doente -->
                <b-row class="mb-3">
                    <b-col><strong>Email do Doente:</strong></b-col>
                    <b-col>
                        <select v-model="observacao['doenteEmail']" required>
                        <option v-for="(doente, index) in this.doentes">
                            {{doente.email}}
                        </option>
                    </select>
                    </b-col>
                </b-row>

                <!-- Dado Biomedico -->
                <b-row class="mb-3">
                    <b-col><strong>Dado Biomédico:</strong></b-col>
                    <b-col>
                        <select v-model="observacao['nomeDadoBiomedico']" @change="updateQualifiers()" required>
                            <option v-for="dado in this.dados">
                                {{dado.nome}}
                            </option>
                        </select>
                    </b-col>
                </b-row>

                <!-- Data -->
                <b-row class="mb-3">
                    <b-col><strong>Data:</strong></b-col>
                    <b-col>
                        <b-form-datepicker id="data" v-model="observacao['data']" class="mb-2"></b-form-datepicker>
                    </b-col>
                </b-row>

                <!-- Valor Quantitativo -->
                <b-row class="mb-3">
                    <b-col><strong>Valor:</strong></b-col>
                    <b-col><b-input v-model="observacao['valorQuantitativo']" type="number" step="any" required/></b-col>
                </b-row>
                <b-row><p v-if="observacao['valorQuantitativo']>dadoBiomedico.maximo || observacao['valorQuantitativo']<dadoBiomedico.minimo" style="color:red"> Valor tem que estar entre {{dadoBiomedico.minimo}} e {{dadoBiomedico.maximo}}</p></b-row>


                <!-- Valor Qualitativo -->
                <b-row class="mb-3">
                    <b-col><strong>Valor Qualitativo:</strong></b-col>
                    <b-col>
                        <select v-model="observacao['valorQualitativo']" required>
                            <option v-for="qualificador in this.dadoBiomedico.qualificadores">
                                {{qualificador}}
                            </option>
                        </select>
                    </b-col>
                </b-row>



                <!-- Botões -->
                <b-row class="mb-3">
                    <b-col>
                        <nuxt-link to="/observacoes">
                            <button class="btn btn-warning">Return</button>
                        </nuxt-link>
                    </b-col>

                    <b-col>
                        <b-button class="btn btn-primary" @click="reset()">RESET</b-button>
                        <button type="submit"  class="btn btn-light" :disabled="!isFormValid">UPDATE</button>
                    </b-col>
                </b-row>
            </b-form>
        </b-container>
</template>

<script>
export default {
    data () {
        return {
            doentes: [],
            dados:[],
            dadoBiomedico: [],
            observacao: {
                'doenteEmail': null,
                'nomeDadoBiomedico': null,
                'data': null,
                'valorQuantitativo': null,
                'valorQualitativo': null,
            },
        }
    },
    created () {
        this.$axios.$get('/api/dadosbiomedicos').then(dados => {
            this.dados = dados
        })
        this.$axios.$get('api/observacoes/' + this.$route.params.id).then((observacao) => {
            this.observacao = observacao

            this.$axios.$get('api/dadosbiomedicos/' + this.observacao['nomeDadoBiomedico']).then((dadoBiomedico) => {
                this.dadoBiomedico = dadoBiomedico
            })
        })
        this.$axios.$get('/api/doentes').then(doentes => {
            this.doentes = doentes
        })

    },
    computed:{
        stateMinimumMaximum() {
            return (!(this.observacao['valorQuantitativo']>this.dadoBiomedico.maximo || this.observacao['valorQuantitativo']<this.dadoBiomedico.minimo))
        },
        isFormValid() {

            return (this.stateMinimumMaximum)
        }
    },
    methods: {
        reset(){
            this.observacao.valorQuantitativo = 0
        },
        updateQualifiers(){
            this.$axios.$get('api/dadosbiomedicos/' + this.observacao['nomeDadoBiomedico']).then((dadoBiomedico) => {
                this.dadoBiomedico = dadoBiomedico
            })
        },
        update: function () {
            this.$axios.$put('api/observacoes/' + this.$route.params.id + '/update', {

                'doenteEmail': this.observacao.doenteEmail,
                'profissionalDeSaudeEmail':this.$auth.user.sub,
                'nomeDadoBiomedico': this.observacao.nomeDadoBiomedico,
                'data': this.observacao.data,
                'valorQuantitativo': this.observacao.valorQuantitativo,
                'valorQualitativo': this.observacao.valorQualitativo,

            }).then(() => {
                this.$router.push('/observacoes')
                alert("Observacao atualizada!")
            })
        }
    }
}
</script>

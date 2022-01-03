<template>
    <b-container>
        <h1 class="mb-4 mt-4">
            Criar Observação
        </h1>
        <b-form @submit.prevent="onSubmit" :disabled="!isFormValid">

            <!-- Email Doente -->
            <b-row class="mb-3">
                <b-col><strong>Email do Doente:</strong></b-col>
                <b-col>
                    <select v-model="observacao.doenteEmail" required>
                        <option v-for="doente in this.doentes" >
                            {{doente.email}}
                        </option>
                    </select>
                </b-col>
            </b-row>

            <!-- Dado Biomedico -->
            <b-row class="mb-3">
                <b-col><strong>Dado Biomédico:</strong></b-col>
                <b-col>
                    <select v-model="observacao.nomeDadoBiomedico" @change="updateQualifiers()" required>
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
                    <b-form-datepicker id="data" v-model="observacao.data" class="mb-2" aria-required="true"></b-form-datepicker>
                </b-col>
            </b-row>

            <!-- Valor Quantitativo -->
            <b-row class="mb-3">
                <b-col><strong>Valor:</strong></b-col>
                <b-col><b-input v-model="observacao.valorQuantitativo" type="number" step="any" required/></b-col>
            </b-row>
            <b-row><p v-if="observacao.valorQuantitativo>dadoBiomedico.maximo || observacao.valorQuantitativo<dadoBiomedico.minimo" style="color:red"> Valor tem que estar entre {{dadoBiomedico.minimo}} e {{dadoBiomedico.maximo}}</p></b-row>


            <!-- Valor Qualitativo -->
            <div v-if="observacao.nomeDadoBiomedico!=null">
            <b-row class="mb-3">
                <b-col><strong>Valor Qualitativo:</strong></b-col>
                <b-col>
                    <select v-model="observacao.valorQualitativo" required>
                        <option v-for="qualificador in this.dadoBiomedico.qualificadores" >
                            {{qualificador}}
                        </option>
                    </select>
                </b-col>
            </b-row>
            </div>



            <!-- Botões -->
            <b-row class="mb-3">
                <b-col>
                    <nuxt-link to="/observacoes">
                        <button class="btn btn-warning">Return</button>
                    </nuxt-link>
                </b-col>

                <b-col>
                    <b-button class="btn btn-primary" @click="reset()">RESET</b-button>
                    <b-button variant="primary" type="submit" :disabled="!isFormValid">Create</b-button>
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
                doenteEmail: null,
                profissionalDeSaudeEmail: this.$auth.user.sub,
                nomeDadoBiomedico: null,
                data: null,
                valorQuantitativo: null,
                valorQualitativo: null,
            },
        }
    },
    created () {
        this.$axios.$get('/api/dadosbiomedicos').then(dados => {
            this.dados = dados
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
            return (this.stateMinimumMaximum && this.observacao.data != null)
        }
    },
    methods: {
        reset(){
            this.observacao.valorQuantitativo = 0
            this.nomeDadoBiomedico = null
        },
        updateQualifiers(){
            this.$axios.$get('api/dadosbiomedicos/' + this.observacao['nomeDadoBiomedico']).then((dadoBiomedico) => {
                this.dadoBiomedico = dadoBiomedico
            })
        },
        onSubmit() {
            this.$axios.$post('api/observacoes/', this.observacao)
            .then(() => {
                this.$router.push('/observacoes')
                alert("Observacao criada!")
            })
        }
    }
}
</script>

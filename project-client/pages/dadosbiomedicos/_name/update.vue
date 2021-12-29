<template>
    <div>
        <b-container>
            <h1 class="mb-4 mt-4">
                Update Dado Biomédico '{{ this.nome }}'
            </h1>
            <b-form @submit.prevent="update" :disabled="!isFormValid">

                <!-- Descrição -->
                <b-row class="mb-3">
                    <b-col><strong>Descrição:</strong></b-col>
                    <b-col><b-input v-model="dadoBiomedico['descricao']" type="text" required/></b-col>
                </b-row>

                <!-- Valor minimo -->
                <b-row class="mb-3">
                    <b-col><strong>Valor mínimo:</strong></b-col>
                    <b-col><b-input v-model="dadoBiomedico['minimo']" type="number" step="any" required/></b-col>
                </b-row>
                <b-row><p v-if="dadoBiomedico['minimo']>dadoBiomedico['maximo']" style="color:red"> Valor mínimo tem de ser menor que o valor máximo</p></b-row>

                <!-- Valor maximo -->
                <b-row class="mb-3">
                    <b-col><strong>Valor máximo:</strong></b-col>
                    <b-col><b-input v-model="dadoBiomedico['maximo']" type="number" step="any" required/></b-col>
                </b-row>
                <b-row><p v-if="dadoBiomedico['maximo']<dadoBiomedico['minimo']" style="color:red"> Valor máximo tem de ser maior que o valor mínimo</p></b-row>

                <!-- Unidade de Medida -->
                <b-row class="mb-3">
                    <b-col><strong>Unidade de medida:</strong></b-col>
                    <b-col><b-input v-model="dadoBiomedico['unidadeMedida']" type="text" required/></b-col>
                </b-row>

                <!-- Qualificadores -->
                <b-row class="mb-3">
                    <b-col><strong>Lista de Qualificadores:</strong></b-col>
                    <b-col><b-input v-model="qualifier" type="text"/>
                        <b-button variant="success" v-if="qualifier!=''" @click="addQualifier(qualifier)">Add Qualifier</b-button>
                    </b-col>

                <table id="app" class="display table">
                    <tbody>
                    <tr v-for="(dados,index) in this.dadoBiomedico.qualificadores">
                        <td> {{ dados }}</td><b-button pill variant="danger" @click="removeQualifier(index)">X</b-button>

                    </tr>
                    <div v-if="this.dadoBiomedico.qualificadores==''" style="color:red">Nenhum qualificador adicionado</div>
                    </tbody>
                </table>
                </b-row>

                <!-- Botões -->
                <b-row class="mb-3">
                    <b-col>
                        <nuxt-link to="/dadosBiomedicos">
                            <button class="btn btn-warning">Return</button>
                        </nuxt-link>
                    </b-col>

                    <b-col>
                        <button type="reset" class="btn btn-primary">RESET</button>
                        <button type="submit"  class="btn btn-light" :disabled="!isFormValid">UPDATE</button>
                    </b-col>
                </b-row>
            </b-form>
        </b-container>
    </div>
</template>

<script>
export default {
    data () {
        return {
            nome: null,
            qualifier: "",
            dadoBiomedico: {
                'descricao':null,
                'minimo': null,
                'maximo': null,
                'unidadeMedida': null,
                'qualificadores': [],
            },
        }
    },
    created () {
        this.$axios.$get('api/dadosbiomedicos/' + this.$route.params.name).then((dadoBiomedico) => {
            this.nome = this.$route.params.name.replace(/_/g,' ');
            this.dadoBiomedico = dadoBiomedico
        })
    },
    computed:{
        stateMinimumMaximum() {
            return this.dadoBiomedico.minimo <= this.dadoBiomedico.maximo
        },
        isFormValid() {
            return (this.stateMinimumMaximum &&
                this.dadoBiomedico.qualificadores!='' &&
                this.dadoBiomedico.descricao!='')
        }
    },
    methods: {
        removeQualifier(index){
            this.dadoBiomedico.qualificadores.splice(index, 1);
        },
        addQualifier(input) {
            for (let i = 0; i < this.dadoBiomedico.qualificadores.length; i++) {
                if(input == this.dadoBiomedico.qualificadores[i]){
                    alert("Não pode repetir qualificadores!")
                    return 0
                }
            }
            this.dadoBiomedico.qualificadores.push(input);
            this.qualifier='';
        },
        update: function () {
            this.$axios.$put('api/dadosbiomedicos/' + this.$route.params.name + '/update', {
                'nome': this.dadoBiomedico.nome,
                'descricao':this.dadoBiomedico.descricao,
                'minimo': this.dadoBiomedico.minimo,
                'maximo': this.dadoBiomedico.maximo,
                'unidadeMedida': this.dadoBiomedico.unidadeMedida,
                'qualificadores': this.dadoBiomedico.qualificadores,
            }).then(() =>
                this.$router.push('/dadosBiomedicos')
            )
        }
    }
}
</script>

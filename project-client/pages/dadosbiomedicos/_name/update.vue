<template>
    <div>
        <b-container>
            <h1 class="mb-4 mt-4">
                Update Dado Biomédico '{{ this.nome }}'
            </h1>
            <form @submit.prevent="create">
                <b-row><p v-if="(dadoBiomedico['descricao'] && dadoBiomedico['measuringUnit']) == ''"
                          style="color:red">Por favor preencha os campos!</p></b-row>
                <b-row class="mb-3">
                    <b-col><strong>Descrição:</strong></b-col>
                    <b-col><input v-model="dadoBiomedico['descricao']" type="text"/></b-col>
                </b-row>

                <b-row class="mb-3">
                    <b-col><strong>Valor mínimo:</strong></b-col>
                    <b-col><input v-model="dadoBiomedico['minimum']" type="number"/></b-col>
                </b-row>
                <b-row><p v-if="dadoBiomedico['minimum']>dadoBiomedico['maximum']" style="color:red"> Valor mínimo tem de ser menor que o valor máximo</p></b-row>
                <b-row class="mb-3">
                    <b-col><strong>Valor máximo:</strong></b-col>
                    <b-col><input v-model="dadoBiomedico['maximum']" type="number"/></b-col>

                </b-row>
                <b-row><p v-if="dadoBiomedico['maximum']<dadoBiomedico['minimum']" style="color:red"> Valor máximo tem de ser maior que o valor mínimo</p></b-row>
                <b-row class="mb-3">
                    <b-col><strong>Unidade de medida:</strong></b-col>
                    <b-col><input v-model="dadoBiomedico['measuringUnit']" type="text"/></b-col>
                </b-row>


                <b-row class="mb-3">
                    <b-col><strong>Lista de Qualificadores:</strong></b-col>
                    <b-col><input v-model="qualifier" type="text"/>
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





                <b-row class="mb-3">
                    <b-col>
                        <nuxt-link to="/dadosBiomedicos">
                            <button class="btn btn-warning">Return</button>
                        </nuxt-link>
                    </b-col>
                    <b-col>
                        <button type="reset" class="btn btn-primary">RESET</button>
                        <button @click.prevent="update" class="btn btn-light" :disabled="!isFormValid">UPDATE</button>
                    </b-col>
                </b-row>
            </form>
        </b-container>
    </div>
</template>

<script>
export default {
    data () {
        return {
            nome: null,
            qualifier: null,
            dadoBiomedico: {
                'descricao':null,
                'minimum': null,
                'maximum': null,
                'measuringUnit': null,
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
        stateName() {
            if(!this.dadoBiomedico.name)
            {
                return null
            }
            return this.dadoBiomedico.name.length >= 0
        },
        stateDescription(){
            if (!this.dadoBiomedico.descricao){
                return null
            }
            return this.dadoBiomedico.descricao.length >= 0
        },
        stateMinimumMaximum() {
            return this.dadoBiomedico.minimum <= this.dadoBiomedico.maximum
        },
        stateQualificador(){
            if(!this.dadoBiomedico.qualificadores)
            {
                return null
            }
            return this.dadoBiomedico.name.length >= 0
        },
        stateMeasuringUnit() {
            if(!this.dadoBiomedico.measuringUnit)
            {
                return null
            }
            return this.dadoBiomedico.measuringUnit.length >= 0
        },
        isFormValid() {
            return (this.stateName &&
                this.stateMeasuringUnit &&
                this.stateMinimumMaximum &&
                this.dadoBiomedico.qualificadores!='' &&
                this.dadoBiomedico.descricao!='')
        }
    },
    methods: {
        removeQualifier(index){
            this.dadoBiomedico.qualificadores.splice(index, 1);
        },
        addQualifier(input) {
            this.dadoBiomedico.qualificadores.push(input);
            this.qualifier='';
        },
        update: function () {
            this.$axios.$put('api/dadosbiomedicos/' + this.$route.params.name + '/update', {
                'name': this.dadoBiomedico.name,
                'descricao':this.dadoBiomedico.descricao,
                'minimum': this.dadoBiomedico.minimum,
                'maximum': this.dadoBiomedico.maximum,
                'measuringUnit': this.dadoBiomedico.measuringUnit,
                'qualificadores': this.dadoBiomedico.qualificadores,
            }).then(() =>
                this.$router.push('/dadosBiomedicos')
            )
        }
    }
}
</script>

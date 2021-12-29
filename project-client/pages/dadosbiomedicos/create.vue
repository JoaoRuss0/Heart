<template>
    <b-container class="mt-4">
        <h1>
            Criar novo Dado Biomedico
        </h1>
        <b-form @submit.prevent="onSubmit" :disabled="!isFormValid">

            <b-form-group
                id="inputNameGroup"
                label="Nome:"
                label-for="inputName"
                :state="stateName"
            >
                <b-form-input
                    id="inputName"
                    v-model="dadoBiomedico.name"
                    placeholder="Escreva o nome"
                    required
                ></b-form-input>
                <div v-if="estadoNome()" style="color:red">
                    <p>Nomes usados:</p>
                    <div v-for="nome in this.todos">
                        <li>{{nome.name}}</li>
                    </div>
                </div>
            </b-form-group>

            <b-form-group
                id="inputDescriptionGroup"
                label="Descrição:"
                label-for="inputDescription"
                :state="stateDescription"
            >
                <b-form-input
                    id="inputDescription"
                    v-model="dadoBiomedico.descricao"
                    placeholder="Escreva a descrição"

                    required
                ></b-form-input>
            </b-form-group>

            <b-form-group
                id="inputMinimumGroup"
                label="Valor mínimo:"
                label-for="inputMinimum"
                :invalid-feedback="invalidFeedbackMinimum"
                :state="stateMinimumMaximum"
            >
                <b-form-input
                    id="inputMinimum"
                    v-model="dadoBiomedico.minimum"
                    type="number"
                    step="0.001"
                    required
                ></b-form-input>
            </b-form-group>

            <b-form-group
                id="inputMaximumGroup"
                label="Valor máximo:"
                label-for="inputMaximum"
                :invalid-feedback="invalidFeedbackMaximum"
                :state="stateMinimumMaximum"
            >
                <b-form-input
                    id="inputMaximum"
                    v-model="dadoBiomedico.maximum"
                    type="number"
                    step="0.001"

                    required
                ></b-form-input>
            </b-form-group>

            <b-form-group
                id="inputMeasuringUnitGroup"
                label="Unidade de medida:"
                label-for="inputMeasuringUnit"
                :state="stateMeasuringUnit"
            >

                <b-form-input
                    id="inputMeasuringUnit"
                    v-model="dadoBiomedico.measuringUnit"
                    placeholder="Escreva a unidade de medida"
                    required
                ></b-form-input>
            </b-form-group>

            <b-form-group
                id="qualificador"
                label="Qualificador:"
                :state="stateQualificador"
            >

               <b-form-input
                    id="qualificador"
                    v-model="qualifier"
                    placeholder="Escreva o Qualificador"

                ></b-form-input>
                          <br>
                <b-button variant="success" v-if="qualifier!=''" @click="addQualifier(qualifier)">Add Qualifier</b-button>
                <div v-if="this.dadoBiomedico.qualificadores==''" style="color:red">Nenhum qualificador adicionado</div>

            </b-form-group>

            <div v-if="this.dadoBiomedico.qualificadores!=''">
                <p>Lista de Qualificadores:</p>
                <table id="app" class="display table">
                    <tbody>
                    <tr v-for="(dados,index) in this.dadoBiomedico.qualificadores">
                        <td> {{ dados }}</td><b-button pill variant="danger" @click="removeQualifier(index)">X</b-button>

                    </tr>
                    </tbody>
                </table>
            </div>



            <b-button type="submit" variant="primary" :disabled="!isFormValid">Criar</b-button>
            <b-button type="reset" variant="danger">Apagar</b-button>
            <nuxt-link to="/dadosbiomedicos">
                <button class="btn btn-warning">Voltar</button>
            </nuxt-link>
        </b-form>
    </b-container>
</template>


<script>

export default {

    data() {
        return {
            qualifier: "",
            todos:'',
            dadoBiomedico: {
                name: '',
                descricao:'',
                minimum: 0.0,
                maximum: 0.0,
                measuringUnit: '',
                qualificadores: []
            },
        }
    },

    created(){
        this.$axios.$get('/api/dadosbiomedicos').then(todos => {
            this.todos = todos
        })
    },
    computed: {
        stateName() {
            if(!this.dadoBiomedico.name)
            {
                return null
            }
            return this.dadoBiomedico.name.length >= 0
        },
        stateDescription(){
            if (!this.dadoBiomedico.description){
                return null
            }
            return this.dadoBiomedico.description.length >= 0
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
        invalidFeedbackMinimum() {
            if(this.stateMinimumMaximum)
            {
                return ""
            }
            return "Minimum value has to be less than or equal to Maximum value."
        },
        invalidFeedbackMaximum() {
            if(this.stateMinimumMaximum)
            {
                return ""
            }
            return "Maximum value has to be greater than or equal to Minimum value."
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
        estadoNome(){
            for (let i = 0; i < this.todos.length; i++) {
                if(this.dadoBiomedico.name.toUpperCase() == this.todos[i].name.toUpperCase()){
                    alert("Atenção, o nome do dado não pode ser igual a um dado já criado")
                    return 1
                }
            }
            return 0;
        },
        removeQualifier(index){
            this.dadoBiomedico.qualificadores.splice(index, 1);
        },
        addQualifier(input) {
            this.dadoBiomedico.qualificadores.push(input);
            this.qualifier='';
        },
        onSubmit(event) {
            this.dadoBiomedico.name = this.dadoBiomedico.name.replace(/\s/g,'_');
            this.$axios.$post('api/dadosbiomedicos/', this.dadoBiomedico)
            .then(() => {
                this.$router.push("/dadosbiomedicos")
                alert("Dado Biomedico criado")
            })
            .catch( error => {
                alert(error)
            })
        }
    },

}
</script>

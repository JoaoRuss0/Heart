<template>
    <div v-if="this.$auth.user.groups[0] != 'Doente'">
    <b-container class="mt-4">
        <h1>
            Criar novo Dado Biomedico
        </h1>
        <b-form @submit.prevent="onSubmit" :disabled="!isFormValid">

            <!-- Nome -->
            <b-form-group
                id="inputNameGroup"
                label="Nome:"
                label-for="inputName"
            >
                <b-form-input
                    id="inputName"
                    v-model="dadoBiomedico.nome"
                    placeholder="Escreva o nome"
                    required
                ></b-form-input>


                <div v-if="estadoNome()" style="color:red">
                    <p>Nomes usados:</p>
                    <div v-for="nome in this.todos">
                        <li>{{nome.nome}}</li>
                    </div>
                </div>
            </b-form-group>

            <!-- Descricao -->
            <b-form-group
                id="inputDescriptionGroup"
                label="Descrição:"
                label-for="inputDescription"

            >
                <b-form-input
                    id="inputDescription"
                    v-model="dadoBiomedico.descricao"
                    placeholder="Escreva a descrição"
                    required
                ></b-form-input>
            </b-form-group>

            <!-- Minimo -->
            <b-form-group
                id="inputMinimumGroup"
                label="Valor mínimo:"
                label-for="inputMinimum"
                :invalid-feedback="invalidFeedbackMinimum"
                :state="stateMinimumMaximum"
            >
                <b-form-input
                    id="inputMinimum"
                    v-model="dadoBiomedico.minimo"
                    type="number"
                    step="any"
                    required
                ></b-form-input>
            </b-form-group>

            <!-- Maximo -->
            <b-form-group
                id="inputMaximumGroup"
                label="Valor máximo:"
                label-for="inputMaximum"
                :invalid-feedback="invalidFeedbackMaximum"
                :state="stateMinimumMaximum"
            >
                <b-form-input
                    id="inputMaximum"
                    v-model="dadoBiomedico.maximo"
                    type="number"
                    step="any"

                    required
                ></b-form-input>
            </b-form-group>

            <!-- Unidade de Medida -->
            <b-form-group
                id="inputMeasuringUnitGroup"
                label="Unidade de medida:"
                label-for="inputMeasuringUnit"

            >

                <b-form-input
                    id="inputMeasuringUnit"
                    v-model="dadoBiomedico.unidadeMedida"
                    placeholder="Escreva a unidade de medida"
                    required
                ></b-form-input>
            </b-form-group>

            <!-- Qualificadores -->
            <b-form-group
                id="qualificador"
                label="Qualificador:"

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

            <!-- Tabela Qualificadores -->
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

            <!-- Botões -->
            <b-button type="submit" variant="primary" :disabled="!isFormValid">Criar</b-button>
            <b-button type="reset" variant="danger">Apagar</b-button>
            <nuxt-link to="/dadosbiomedicos">
                <button class="btn btn-warning">Voltar</button>
            </nuxt-link>
        </b-form>
    </b-container>
    </div>
    <div v-else><h1>Sem acesso a esta página</h1></div>
</template>


<script>

export default {

    data() {
        return {
            qualifier: "",
            todos: [],
            dadoBiomedico: {
                nome: '',
                descricao:'',
                minimo: 0.0,
                maximo: 0.0,
                unidadeMedida: '',
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
        stateMinimumMaximum() {
            return this.dadoBiomedico.minimo <= this.dadoBiomedico.maximo
        },
        invalidFeedbackMinimum() {
            if(this.stateMinimumMaximum)
            {
                return ""
            }
            return "Valor mínimo tem de ser menor ou igual ao máximo."
        },
        invalidFeedbackMaximum() {
            if(this.stateMinimumMaximum)
            {
                return ""
            }
            return "Valor máximo tem de ser maior ou igual ao mínimo."
        },
        isFormValid() {
            return (this.stateMinimumMaximum &&this.dadoBiomedico.qualificadores!='' && !this.estadoNome())
        }
    },
    methods: {
        estadoNome(){
            for (let i = 0; i < this.todos.length; i++) {
                if(this.dadoBiomedico.nome.toUpperCase() === this.todos[i].nome.toUpperCase()){
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
            for (let i = 0; i < this.dadoBiomedico.qualificadores.length; i++) {
                if(input == this.dadoBiomedico.qualificadores[i]){
                    alert("Não pode repetir qualificadores!")
                    return 0
                }
            }
            this.dadoBiomedico.qualificadores.push(input);
            this.qualifier='';
        },
        onSubmit(event) {
            this.dadoBiomedico.nome = this.dadoBiomedico.nome.replace(/\s/g,'_');
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

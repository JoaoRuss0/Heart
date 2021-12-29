<template>
    <div>
        <b-container fluid="sm">
            <div v-if="dadoBiomedico">
                <h1 class="mb-4 mt-4">
                    Nome "{{ dadoBiomedico['name'] }}"
                </h1>
                <b-row class="mb-3">
                    <b-col><strong>Descrição:</strong></b-col> <b-col>{{ dadoBiomedico['descricao'] }}</b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Valor mínimo:</strong></b-col> <b-col>{{ dadoBiomedico['minimum'] }}</b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Valor máximo:</strong></b-col> <b-col>{{ dadoBiomedico['maximum'] }}</b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Unidade de medida:</strong></b-col> <b-col>{{ dadoBiomedico['measuringUnit'] }}</b-col>
                </b-row>

                <b-row class="mb-3">
                <b-col><strong>Lista de Qualificadores:</strong></b-col>
                <table id="app" class="display table">
                    <tbody>
                    <tr v-for="dados in dadoBiomedico['qualificadores']">
                        <b-col><li> {{ dados }}</li></b-col>
                    </tr>
                    </tbody>
                </table>
                </b-row>




                <b-row class="mb-3">
                    <b-col>
                        <nuxt-link to="/dadosbiomedicos">
                            <button class="btn btn-warning">Return</button>
                        </nuxt-link>
                    </b-col>
                </b-row>
            </div>
        </b-container>
    </div>
</template>

<script>
export default {
    data () {
        return {
            dadoBiomedico: null,

        }
    },
    created () {

        this.$axios.$get('api/dadosbiomedicos/' + this.$route.params.name).then((dadoBiomedico) => {

            console.log(dadoBiomedico);
            this.dadoBiomedico = dadoBiomedico
        })
    },
}
</script>

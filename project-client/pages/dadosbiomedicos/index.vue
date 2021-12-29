<template>
    <b-container>
        <h1>Lista de Dados Biomedicos:</h1>
        <template v-if="dados.length > 0">
            <b-row :no-gutters="true">
                <b-table
                    hover
                    bordered
                    selectable
                    no-border-collapse
                    headVariant="dark"
                    primary-key="email"
                    select-mode="single"
                    selected-variant="primary"
                    @row-selected="onRowSelected"
                    :items="dados"
                    :fields="fields"

                >
                    <template #table-busy>
                        <spinner />
                    </template>
                </b-table>
            </b-row>
        </template>
        <template v-else>
            <p class="text-danger">Não há dados para listar</p>
        </template>
        <b-row :no-gutters="true">
            <b-col class="text-left">
                <b-button variant="success" @click="pushRoute(`/dadosbiomedicos/create/`)">Criar Dado Biomedico</b-button>
            </b-col>
        </b-row>
        <template v-if="dados.length > 0">
            <b-col class="text-right">

                <b-button variant="danger" :disabled="selectedRow.length == 0" @click="deleteDado()">Delete</b-button>
                <b-button variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute(`/dadosBiomedicos/${selectedRow[0].nome}`)">Details</b-button>
                <b-button variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute(`/dadosBiomedicos/${selectedRow[0].nome}/update`)">Update</b-button>
            </b-col>
        </template>

    </b-container>
</template>



<script>
export default {
    data() {
        return {
            dados: [],
            fields: ["nome", "descricao", "maximo", "minimo", "unidadeMedida", "qualificadores"],
            selectedRow:[],
        }
    },
    created() {
        this.$axios.$get('/api/dadosbiomedicos').then(dados => {
            this.dados = dados
        })
    },
    methods: {
        onRowSelected(selectedRow) {
            this.selectedRow = selectedRow
        },
        pushRoute(route) {
            this.$router.push(route)
        },

        deleteDado() {
            this.$axios.$delete(`/api/dadosbiomedicos/${this.selectedRow[0].nome}`).then(response => {
                this.$router.go()
            }).catch(error => console.log(error))
        }


    }
}
</script>

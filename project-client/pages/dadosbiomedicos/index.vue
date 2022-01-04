<template>
    <b-container>
        <h1>List of Dados Biomedicos:</h1>

        <template v-if="dados.length == 0 && dadosLoading == false">
            <p class="text-danger">No observações to show.</p>
        </template>
        <template v-else>
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
                    :busy="dadosLoading"
                >
                    <template #table-busy>
                        <Spinner />
                    </template>
                </b-table>
            </b-row>

            <b-row :no-gutters="true">
                <b-col class="text-left">
                    <b-button variant="success" v-if="this.$store.state.auth.loggedIn == true && this.$auth.user.groups[0] == 'Administrador'" @click="pushRoute(`/dadosbiomedicos/create/`)">Create</b-button>
                </b-col>
                <b-col class="text-right">
                    <b-button variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute(`/dadosBiomedicos/${selectedRow[0].nome}`)">Details</b-button>
                    <template v-if="this.$store.state.auth.loggedIn == true && this.$auth.user.groups[0] == 'Administrador'">
                        <b-button variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute(`/dadosBiomedicos/${selectedRow[0].nome}/update`)">Update</b-button>
                        <b-button variant="danger" :disabled="selectedRow.length == 0" @click="deleteDadoBiomedico(selectedRow[0])">Delete</b-button>
                    </template>
                </b-col>
            </b-row>
        </template>
    </b-container>
</template>

<script>
import dadosBiomedicosIndex from "../../middleware/dadosBiomedicosIndex";
import Spinner from "../../components/Spinner";

export default {
    middleware: dadosBiomedicosIndex,
    components: {Spinner},
    data() {
        return {
            dados: [],
            fields: ["nome", "descricao", "maximo", "minimo", "unidadeMedida", "qualificadores"],
            selectedRow:[],
            dadosLoading: true,
        }
    },
    created() {
        this.$axios.$get('/api/dadosbiomedicos').then(dados => {
            this.dados = dados
            this.dadosLoading = false
        })
    },
    methods: {
        onRowSelected(selectedRow) {
            this.selectedRow = selectedRow
        },
        pushRoute(route) {
            this.$router.push(route)
        },
        deleteDadoBiomedico(row) {
            this.$axios.$delete(`/api/dadosbiomedicos/${row.nome}`).then(response => {

                this.$toast.success("Successfully deleted dado biomedico (" + row.id + ").")

                //reload dados biomedicos list
                this.$axios.$get('/api/dadosbiomedicos').then(dados => {
                    this.dadosLoading = true
                    this.dados = []
                    this.dados.push(...dados)
                    this.dadosLoading = false
                }).catch( error => this.$toast.error("Error loading list of dados biomedicos."))

            }).catch(error => {
                this.$toast.error("Could not delete dado biomedico (" + row.id + ").<\/br>Error: '" + error.response.data + "'")
            })
        }
    }
}
</script>

<template>
    <b-container>
        <h1>List of Observações:</h1>

        <template v-if="observacoes.length == 0 && observacoesLoading == false">
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
                    :items="observacoes"
                    :fields="fields"
                    :busy="observacoesLoading"
                >
                    <template #table-busy>
                        <Spinner/>
                    </template>
                </b-table>
            </b-row>

            <b-row :no-gutters="true">
                <b-col class="text-left">
                    <template v-if="this.$store.state.auth.loggedIn == true && this.$auth.user.groups[0] =='ProfissionalDeSaude'">
                        <b-button variant="success" @click="pushRoute(`/observacoes/create/`)">Create Observação</b-button>
                        <b-button variant="success" :disabled="selectedRow.length == 0" @click="pushRoute(`/prescricoes/observacoes/${selectedRow[0].id}/create`)">Create Prescrição</b-button>
                    </template>
                </b-col>
                <b-col class="text-right">
                    <b-button variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute(`/observacoes/${selectedRow[0].id}`)">Details</b-button>
                    <template v-if="this.$store.state.auth.loggedIn == true && this.$auth.user.groups[0] =='ProfissionalDeSaude'">
                        <b-button variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute(`/observacoes/${selectedRow[0].id}/update`)">Update</b-button>
                        <b-button variant="danger" :disabled="selectedRow.length == 0" @click="deleteObservacao(selectedRow[0])">Delete</b-button>
                    </template>
                </b-col>
            </b-row>
        </template>
    </b-container>
</template>

<script>
import Spinner from "../../components/Spinner";

export default {
    components: {Spinner},
    data() {
        return {
            observacoes: [],
            fields: ["id","doenteEmail", "profissionalDeSaudeEmail", "nomeDadoBiomedico", "data", "valorQuantitativo", "valorQualitativo"],
            selectedRow:[],
            observacoesLoading: true
        }
    },
    created() {
        this.$axios.$get('/api/observacoes').then(observacoes => {
            this.observacoes = observacoes
            this.observacoesLoading = false
        })
    },
    methods: {
        onRowSelected(selectedRow) {
            this.selectedRow = selectedRow
        },
        pushRoute(route) {
            this.$router.push(route)
        },

        deleteObservacao(row) {
            this.$axios.$delete(`/api/observacoes/${row.id}`).then(response => {

                this.$toast.success("A observação foi apagada com sucesso!")

                //reload observações list
                this.$axios.$get('/api/observacoes').then(observacoes => {
                    this.observacoesLoading = true
                    this.observacoes = []
                    this.observacoes.push(...observacoes)
                    this.observacoesLoading = false
                }).catch( error => this.$toast.error("Error loading list of observações."))

            }).catch(error => {
                this.$toast.error("Could not delete observação (" + row.id + ").<\/br>Error: '" + error.response.data + "'")
            })
        }
    }
}
</script>

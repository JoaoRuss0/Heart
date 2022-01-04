<template>
    <b-container>
        <h1>List of Prescrições:</h1>

        <b-row :no-gutters="true">
            <template v-if="prescricoes.length == 0 && prescricoesLoading == false">
                <p class="text-danger">No prescrições to show.</p>
            </template>
            <template v-else>
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
                    :items="prescricoes"
                    :fields="fields"
                    :busy="prescricoesLoading"
                >
                    <template #table-busy>
                        <Spinner />
                    </template>
                </b-table>
            </template>
        </b-row>
        <b-row :no-gutters="true">
            <template v-if="prescricoes.length > 0">
                <b-col class="text-right">
                    <b-button  variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute(`/prescricoes/${selectedRow[0].id}/`)">Details</b-button>
                    <template v-if="this.$store.state.auth.loggedIn == true && this.$auth.user.groups[0] == 'ProfissionalDeSaude'">
                        <b-button variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute(`/prescricoes/${selectedRow[0].id}/update`)">Update</b-button>
                        <b-button variant="danger" :disabled="selectedRow.length == 0"  @click="deletePrescricoes(selectedRow[0])">Delete</b-button>
                    </template>
                </b-col>
            </template>
        </b-row>
    </b-container>
</template>

<script>
import Spinner from "../../components/Spinner";

export default {
    components: {Spinner},
    data() {
        return {
            prescricoes: [],
            fields: ["id", "comentario", "doenteEmail", "dataInicio", "dataFinal", "tipoPrescricao", "profissionalDeSaudeEmail", "observacaoID"],
            selectedRow: [],
            prescricoesLoading: true
        }
    },
    created() {
        this.$axios.$get('/api/prescricoes').then(prescricoes => {
            this.prescricoes = prescricoes
            this.prescricoesLoading= false
        })
    },
    methods: {
        onRowSelected(selectedRow) {
            this.selectedRow = selectedRow
        },
        pushRoute(route) {
            this.$router.push(route)
        },
        deletePrescricoes(row) {
            this.$axios.$delete(`/api/prescricoes/${this.selectedRow[0].id}`).then(response => {
                this.selectedRow = []

                this.$toast.success("A prescrição foi apagada com sucesso!")

                //reload prescrições list
                this.$axios.$get('/api/prescricoes').then(prescricoes => {
                    this.prescricoesLoading = true
                    this.prescricoes = []
                    this.prescricoes.push(...prescricoes)
                    this.prescricoesLoading = false
                }).catch( error => this.$toast.error("Error loading list of precrições."))

            }).catch(error => {
                this.$toast.error("Could not delete prescrição (" + row.id + ").<\/br>Error: '" + error.response.data + "'")
            })
        },
    }
}
</script>

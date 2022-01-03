<template>
    <b-container>
        <h1>Lista de Prescrições:</h1>
        <template v-if="prescricoes.length > 0">
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
                    :items="prescricoes"
                    :fields="fields"
                    :busy="prescricoesLoading"
                >
                    <template #table-busy>
                        <spinner />
                    </template>
                </b-table>
            </b-row>
        </template>
        <template v-else>
            <p class="text-danger">Não há prescrições para listar</p>
        </template>
        <b-row :no-gutters="true">
             <template v-if="prescricoes.length > 0">
                 <b-col class="text-right">
                    <b-button  variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute(`/prescricoes/${selectedRow[0].id}/`)">Details</b-button>
                     <b-button v-if="this.$auth.user.groups[0] =='ProfissionalDeSaude'" variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute(`/prescricoes/${selectedRow[0].id}/update`)">Update</b-button>
                     <b-button v-if="this.$auth.user.groups[0] =='ProfissionalDeSaude'" variant="danger" :disabled="selectedRow.length == 0"  @click="deletePrescricoes()">Delete</b-button>
                 </b-col>
             </template>
        </b-row>

    </b-container>
</template>

<script>
export default {
    data() {
        return {
            prescricoes: [],
            fields: ["id", "causa", "doenteEmail", "dataInicio", "dataFinal", "tipoPrescricao", "profissionalDeSaudeEmail", "observacaoID"],
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
        deletePrescricoes() {

            this.$axios.$delete(`/api/prescricoes/${this.selectedRow[0].id}`).then(response => {
                this.$toast.success("A prescrição foi apagada com sucesso!")
                this.$axios.$get('/api/prescricoes').then(prescricoes => {
                    this.prescricoes = []
                    this.prescricoes.push(...prescricoes)
                })
            }).catch(error => console.log(error))
        },
    }
}
</script>

<template>
    <div>
        <b-container>
            <h1>Lista de Observações:</h1>
            <template v-if="observacoes.length > 0">
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

                    >
                        <template #table-busy>
                            <spinner />
                        </template>
                    </b-table>
                </b-row>
            </template>
            <template v-else>
                <p class="text-danger">Não há observacoes para listar</p>
            </template>
            <b-row :no-gutters="true">
                <b-col class="text-left">
                    <b-button v-if="this.$auth.user.groups[0] =='ProfissionalDeSaude' " variant="success" @click="pushRoute(`/observacoes/create/`)">Criar Observação</b-button>
                </b-col>
            </b-row>
            <template v-if="observacoes.length > 0">
                <b-col class="text-right">

                        <b-button v-if="this.$auth.user.groups[0] =='ProfissionalDeSaude'" variant="success" @click="pushRoute(`/prescricoes/observacoes/${selectedRow[0].id}/create`)">Criar Prescrição</b-button>

                    <b-button v-if="this.$auth.user.groups[0] =='ProfissionalDeSaude' " variant="danger" :disabled="selectedRow.length == 0" @click="deleteObs()">Apagar</b-button>
                    <b-button variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute(`/observacoes/${selectedRow[0].id}`)">Detalhes</b-button>
                    <b-button v-if="this.$auth.user.groups[0] =='ProfissionalDeSaude'" variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute(`/observacoes/${selectedRow[0].id}/update`)">Atualizar</b-button>
                </b-col>
            </template>

        </b-container>
    </div>

</template>



<script>

export default {

    data() {
        return {
            observacoes: [],
            fields: ["id","doenteEmail", "profissionalDeSaudeEmail", "nomeDadoBiomedico", "data", "valorQuantitativo", "valorQualitativo"],
            selectedRow:[],
        }
    },
    created() {

        this.$axios.$get('/api/observacoes').then(observacoes => {
            this.observacoes = observacoes
        })
    },
    methods: {
        onRowSelected(selectedRow) {
            this.selectedRow = selectedRow
        },
        pushRoute(route) {
            this.$router.push(route)
        },

        deleteObs() {
            this.$axios.$delete(`/api/observacoes/${this.selectedRow[0].id}`).then(response => {
            this.$toast.success("A observação foi apagada com sucesso!")
                this.$axios.$get('/api/observacoes').then(observacoes => {
                    this.observacoes = []
                    this.observacoes.push(...observacoes)
                })
            }).catch(error => console.log(error))
        }


    }
}
</script>

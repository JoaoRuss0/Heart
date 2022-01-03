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
                    :busy="usersLoading"
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
            <b-col class="text-left">
                <b-button v-if="this.$auth.user.groups[0] =='ProfissionalDeSaude'" variant="success" @click="pushRoute(`/prescricoes/create/`)">Criar Prescrição</b-button>
            </b-col>
             <template v-if="prescricoes.length > 0">
                 <b-col class="text-right">
                    <b-button  variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute(`/prescricoes/${selectedRow[0].id}/`)">Details</b-button>
                     <b-button v-if="this.$auth.user.groups[0] =='ProfissionalDeSaude'" variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute(`/prescricoes/${selectedRow[0].id}/update`)">Update</b-button>
                     <b-button v-if="this.$auth.user.groups[0] =='ProfissionalDeSaude'" variant="danger" :disabled="selectedRow.length == 0"  @click="deleteUser()">Delete</b-button>
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
            fields: ["id", "causa", "doenteEmail", "dataInicio", "dataFinal", "tipoPrescricao", "profissionalDeSaudeEmail"],
            selectedRow: []
        }
    },
    created() {
        this.$axios.$get('/api/prescricoes').then(prescricoes => {
            this.prescricoes = prescricoes
        })
    },
    methods: {
        onRowSelected(selectedRow) {
            this.selectedRow = selectedRow
        },
        pushRoute(route) {
            this.$router.push(route)
        },
        deleteUser() {
            this.$axios.$delete(`/api/prescricoes/${this.selectedRow[0].id}`).then(response => {
                console.log(response)
                this.$router.go()
            }).catch(error => console.log(error))
        },
    }
}
</script>

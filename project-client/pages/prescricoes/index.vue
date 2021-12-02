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
                <b-button variant="success" @click="pushRoute(`/prescricoes/create/`)">Criar Prescrição</b-button>
            </b-col>
            <!--  <template v-if="prescricoes.length > 0">
                 <b-col class="text-right">
                    <b-button variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute(`/prescricoes/${selectedRow[0].email}/`)">Details</b-button>
                     <b-button variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute(`/prescricoes/${selectedRow[0].email}/update`)">Update</b-button>
                     <b-button variant="danger"  :disabled="selectedRow.length == 0" @click="deleteUser()">Delete</b-button>
                 </b-col>
             </template> -->
        </b-row>

    </b-container>
</template>

<script>
export default {
    data() {
        return {
            prescricoes: [],
            fields: ["id", "causa", "doenteEmail", "dataInicio", "dataFinal", "tipoPrescricao"]
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
    }
}
</script>

<template>
    <b-container>
        <h1>List of Doentes:</h1>

        <template v-if="doentes.length == 0 && doentesLoading == false">
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
                    :items="doentes"
                    :fields="fields"
                    :busy="doentesLoading"
                >
                    <template #table-busy>
                        <Spinner />
                    </template>
                </b-table>
            </b-row>

            <b-row :no-gutters="true">
                <b-col class="text-left">
                    <b-button variant="success" @click="pushRoute('create', null)">Create</b-button>
                </b-col>
                <b-col class="text-right">
                    <b-button variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute('view', selectedRow[0])">Details</b-button>
                    <b-button variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute('update', selectedRow[0])">Update</b-button>
                    <b-button variant="danger"  :disabled="selectedRow.length == 0" @click="deleteDoente(selectedRow[0])">Delete</b-button>
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
            doentes: [],
            doentesLoading: true,
            selectedRow: [],
            fields: [{key: "name", label: "Type", sortable: true}, {key: "email", sortable: true}, {key: "idade", sortable: true}, {key: "peso", sortable: true}, {key: "altura", sortable: true}]
        }
    },
    created() {
        this.$axios.$get('/api/doentes').then(doentes => {
            this.doentes = doentes
            this.doentesLoading = false
        })
    },
    methods: {
        onRowSelected(selectedRow) {
            this.selectedRow = selectedRow
        },
        pushRoute(operation, row) {
            if(operation == "create")
            {
                this.$router.push("/users/create/")
                return
            }

            this.$router.push("doentes/" + row.email + "/" + ((operation == "view") ? '' : operation))
        },
        deleteDoente(row) {
            this.$axios.$delete(`/api/doentes/${row.email}`).then(response => {

                this.$toast.success(`Deleted doente (${row.email}) with success!`)

                //reload users list
                this.$axios.$get('/api/users/').then(users => {
                    this.users = users
                    this.usersLoading = false

                }).catch( error => this.$toast.error("Error loading list of doentes."))

            }).catch(error => {
                this.$toast.error("Could not delete doente (" + row.email + ").<\/br>Error: '" + error.response.data + "'")
            })
        }
    }
}
</script>

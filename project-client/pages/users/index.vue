<template>
    <b-container>
        <b-row :no-gutters="true">
            <h1>Current users:</h1>
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
                :items="users"
                :fields="fields"
                :busy="usersLoading"
            >
                <template #table-busy>
                    <spinner />
                </template>
            </b-table>
        </b-row>

        <b-row :no-gutters="true">
            <b-col class="text-left">
                <b-button variant="success" @click="pushRoute('create', null)">Create</b-button>
            </b-col>
            <b-col class="text-right">
                <b-button variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute('view', selectedRow[0])">Details</b-button>
                <b-button variant="warning" :disabled="selectedRow.length == 0 || selectedRow[0].tipo == 'Doente'" @click="pushRoute('update', selectedRow[0])">Update</b-button>
                <b-button variant="danger"  :disabled="selectedRow.length == 0 || selectedRow[0].tipo == 'Doente'" @click="deleteUser(selectedRow[0])">Delete</b-button>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import Spinner from "../../components/Spinner";
import administradorRoutes from "../../middleware/administradorRoutes";
export default {
    components: {Spinner},
    middleware: administradorRoutes,
    data() {
        return {
            users: null,
            usersLoading: true,
            selectedRow: [],
            fields: [{key: "tipo", label: "Type", sortable: true}, {key: "name", sortable: true}, {key: "email", sortable: true}]
        }
    },
    created() {
        this.$axios.$get('/api/users/').then(users => {
            this.users = users
            this.usersLoading = false
        }).catch( error => console.log(error))
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

            var route

            switch (row.tipo)
            {
                case 'Administrador':
                    route = "/administradores/"
                    break;
                case 'ProfissionalDeSaude':
                    route = '/profissionaisdesaude/'
                    break;
                case 'Doente':
                    route = "/doentes/"
                    break;
            }

            route += row.email + "/" + ((operation == "view") ? '' : operation)

            this.$router.push(route)
        },
        deleteUser(row) {
            this.$axios.$delete("/api/" + ((row.tipo == "Administrador") ? "administradores/" : "profissionaisdesaude/") + row.email).then(response => {

                this.$toast.success("Successfully deleted " + row.tipo + " (" + row.email + ").")

                //reload users list
                this.$axios.$get('/api/users/').then(users => {
                    this.users = users
                    this.usersLoading = false
                }).catch( error => console.log(error))

            }).catch(error => {
                this.$toast.error("Could not delete " + row.tipo + " (" + row.email + ").<\/br>Error: '" + error.response.data + "'")
            })
        }
    }
}
</script>

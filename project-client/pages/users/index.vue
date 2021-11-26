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
                :busy="usersLoaded"
            >
                <template #table-busy>
                    <spinner />
                </template>
            </b-table>
        </b-row>

        <b-row :no-gutters="true">
            <b-col class="text-left">
                <b-button variant="success" @click="pushRoute(`/users/create/`)">Create</b-button>
            </b-col>
            <b-col class="text-right">
                <b-button variant="primary" :disabled="selectedRow.length == 0" @click="pushRoute(`/users/${selectedRow[0].email}/`)">Details</b-button>
                <b-button variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute(`/users/${selectedRow[0].email}/update`)">Update</b-button>
                <b-button variant="danger"  :disabled="selectedRow.length == 0" @click="deleteUser()">Delete</b-button>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import Spinner from "../../components/Spinner";
export default {
    components: {Spinner},
    data() {
        return {
            users: null,
            usersLoaded: true,
            selectedRow: [],
            fields: [{key: "tipo", label: "Type", sortable: true}, {key: "name", sortable: true}, {key: "email", sortable: true}]
        }
    },
    created() {
        this.$axios.$get('/api/users/').then(users => {
            this.users = users
            this.usersLoaded = false
        }).catch( error => console.log(error))
    },
    methods: {
        onRowSelected(selectedRow) {
            this.selectedRow = selectedRow
        },
        pushRoute(route) {
            this.$router.push(route)
        },
        deleteUser() {
            this.$axios.$delete(`/api/users/${this.selectedRow[0].email}`).then(response => {
                console.log(response)
            }).catch(error => console.log(error))
        }
    }
}
</script>

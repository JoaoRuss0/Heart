<template>
    <b-container>
        <b-row :no-gutters="true">
            <h1>Doentes:</h1>
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
                <b-button variant="warning" :disabled="selectedRow.length == 0" @click="pushRoute('update', selectedRow[0])">Update</b-button>
                <b-button variant="danger"  :disabled="selectedRow.length == 0" @click="deleteDoente">Delete</b-button>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
export default {
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
        deleteDoente() {
            this.$axios.$delete(`/api/doentes/${this.selectedRow[0].email}`).then(response => {
                this.$auth.
                    //reload users list
                    this.$axios.$get('/api/users/').then(users => {
                    this.users = users
                    this.usersLoading = false
                }).catch( error => console.log(error))

                this.$toast.show(`Deleted user (${selectedRow[0].email}) with success!`)
            }).catch(error => console.log(error))
        }
    }
}
</script>

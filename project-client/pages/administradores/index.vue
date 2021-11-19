<template>
    <b-container>
        <b-table :items="users" :fields="fields"></b-table>
    </b-container>
</template>

<script>
export default {
    data() {
        return {
            users: null,
            fields: ["name", "email"]
        }
    },
}
</script>
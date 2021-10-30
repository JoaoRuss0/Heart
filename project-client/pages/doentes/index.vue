<template>
    <b-container>
        <b-table :items="doentes" :fields="fields"></b-table>
    </b-container>
</template>

<script>
export default {
    data() {
        return {
            doentes: [],
            fields: ["name", "email", "password", "idade", "peso", "altura"]
        }
    },
    created() {
        this.$axios.$get('/api/doentes').then(doentes => {
            this.doentes = doentes
        })
    }
}
</script>

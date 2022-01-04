<template>
    <IndexEntities :user="user"/>
</template>

<script>
import IndexEntities from "../../../components/IndexEntities";


export default {
    components: {IndexEntities},
    data() {
        return {
            user: null,
        }
    },
    created() {
        this.$axios.$get(`/api/administradores/${this.$route.params.email}`).then(user => {
            this.user = user
        }).catch(error => console.log(error))
    }
}
</script>

<template>
    <IndexEntities :user="user"/>
</template>

<script>
import IndexEntities from "../../../components/IndexEntities";
import administradoresViewPostPut from "../../../middleware/administradoresViewPostPut";

export default {
    middleware: administradoresViewPostPut,
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

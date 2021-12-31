<template>
    <IndexProfissionalAdministrador :user="doente"/>
</template>

<script>
import IndexProfissionalAdministrador from "../../../components/IndexEntities";
export default {
    components: {IndexProfissionalAdministrador},
    data() {
        return {
            doente: null,
        }
    },
    created() {
        this.$axios.$get(`/api/doentes/${this.$route.params.email}`).then(doente => {
            this.doente = doente
        })
    }
}
</script>

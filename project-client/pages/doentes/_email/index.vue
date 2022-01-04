<template>
    <IndexEntities :user="doente"/>
</template>

<script>
import IndexEntities from "../../../components/IndexEntities";
import doenteView from "../../../middleware/doenteView";

export default {
    middleware: doenteView,
    components: {IndexEntities},
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

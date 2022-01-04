<template>
    <IndexEntities :user="user"/>
</template>

<script>
import IndexEntities from "../../../components/IndexEntities";
import profissionaisDeSaudeViewPut from "../../../middleware/profissionaisDeSaudeViewPut";

export default {
    middleware: profissionaisDeSaudeViewPut,
    components: {IndexEntities},
    data() {
      return {
          user: null,
      }
    },
    created() {
        this.$axios.$get(`/api/profissionaisdesaude/${this.$route.params.email}`).then(user => {
            this.user = user
        }).catch(error => console.log(error))
    }
}
</script>

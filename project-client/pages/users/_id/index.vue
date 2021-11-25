<template>
    <b-container>
        <h1>User details:</h1>
        <template v-if="user != null">
            <b-form-group v-for="(value, field) in user" :key="field" :id="'fieldset-' + field" :label="field.charAt(0).toUpperCase() + field.slice(1)">
                <b-form-input :disabled="true" :value="value"></b-form-input>
            </b-form-group>
        </template>
        <template v-else>
            <Spinner />
        </template>
    </b-container>
</template>

<script>
export default {
    data() {
        return {
            user: null
        }
    },
    created() {
        this.$axios.$get(`/api/users/${this.$route.params.id}`).then(user => {
            this.user = user
        }).catch(error => console.log(error))
    }
}
</script>

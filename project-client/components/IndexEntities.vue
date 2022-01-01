<template>
    <b-container>
        <h1>User details:</h1>
        <template v-if="user != null">
            <b-form-group v-for="(value, field) in user" :key="field" :id="'fieldset-' + field" :label="field.charAt(0).toUpperCase() + field.slice(1)">
                <b-form-input :disabled="true" :value="value"></b-form-input>
            </b-form-group>
        </template>
        <template v-else>
            <spinner />
        </template>
    </b-container>
</template>

<script>
import Spinner from "./Spinner";
export default {
    props: ["user"],
    components: {Spinner},
}
</script>

<template>
    <b-container>
        <h1>Update User:</h1>
        <template v-if="user != null">
            <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" :disabled="!isFormValid">
                <b-form-group
                    id="fieldset-name"
                    label="Name:"
                    label-for="inputName"
                    valid-feedback="Done!"
                    :invalid-feedback="invalidFeedbackName"
                    :state="stateName"
                >
                    <b-form-input
                        id="inputName"
                        v-model="user.name"
                        placeholder="Enter a name"
                        :state="stateName"
                        required
                    ></b-form-input>
                </b-form-group>
                <div class="text-right">
                    <b-button variant="warning" type="submit" :disabled="!isFormValid">Update</b-button>
                    <b-button variant="danger" type="reset" :disabled="fieldsAreEmpty">Reset</b-button>
                </div>
            </b-form>
        </template>
        <template v-else>
            <spinner />
        </template>
    </b-container>
</template>

<script>
import Spinner from "./Spinner";
export default {
    props: ["user", "resetname"],
    components: {Spinner},
    computed: {
        stateName() {
            if(!this.user.name) {
                return null
            }
            return this.user.name.length >= 3
        },
        invalidFeedbackName() {
            if(!this.stateName) {
                return "Name has to be at least 3 in length."
            }
            return ""
        },
        isFormValid() {
            return this.stateName
        },
        fieldsAreEmpty() {
            return !this.user.name
        }
    },
    methods: {
        onSubmit() {
            this.$emit("update")
        },
        onReset() {
            this.user.name = this.resetname
        }
    }
}
</script>

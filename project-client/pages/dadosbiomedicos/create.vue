<template>
    <b-container class="mt-4">
        <h1>
            Create new dado biomedico
        </h1>
        <b-form @submit.prevent="onSubmit" :disabled="!isFormValid">
            <b-form-group
                id="inputNameGroup"
                label="Name:"
                label-for="inputName"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackName"
                :state="stateName"
            >
                <b-form-input
                    id="inputName"
                    v-model="dadoBiomedico.name"
                    placeholder="Enter name"
                    :state="stateName"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="inputMinimumGroup"
                label="Minimum:"
                label-for="inputMinimum"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackMinimum"
                :state="stateMinimumMaximum"
            >
                <b-form-input
                    id="inputMinimum"
                    v-model="dadoBiomedico.minimum"
                    type="number"
                    step="0.001"
                    :state="stateMinimumMaximum"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="inputMaximumGroup"
                label="Maximum:"
                label-for="inputMaximum"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackMaximum"
                :state="stateMinimumMaximum"
            >
                <b-form-input
                    id="inputMaximum"
                    v-model="dadoBiomedico.maximum"
                    type="number"
                    step="0.001"
                    :state="stateMinimumMaximum"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="inputMeasuringUnitGroup"
                label="Measuring Unit:"
                label-for="inputMeasuringUnit"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackMeasuringUnit"
                :state="stateMeasuringUnit"
            >
                <b-form-input
                    id="inputMeasuringUnit"
                    v-model="dadoBiomedico.measuringUnit"
                    placeholder="Enter measuring unit"
                    :state="stateMeasuringUnit"
                    required
                ></b-form-input>
            </b-form-group>
            <div class="text-right">
                <b-button variant="primary" type="submit" :disabled="!isFormValid">Create</b-button>
                <b-button variant="danger" type="reset">Reset</b-button>
            </div>
        </b-form>
    </b-container>
</template>

<script>
export default {
    data() {
        return {
            dadoBiomedico: {
                name: '',
                minimum: 0.0,
                maximum: 0.0,
                measuringUnit: ''
            }
        }
    },
    computed: {
        stateName() {
            if(!this.dadoBiomedico.name)
            {
                return null
            }
            return this.dadoBiomedico.name.length >= 3
        },
        invalidFeedbackName() {
            if(this.stateName)
            {
                return ""
            }
            return "Name has to be at least 3 in length."
        },
        stateMinimumMaximum() {
            return this.dadoBiomedico.minimum <= this.dadoBiomedico.maximum
        },
        invalidFeedbackMinimum() {
            if(this.stateMinimumMaximum)
            {
                return ""
            }
            return "Minimum value has to be less than or equal to Maximum value."
        },
        invalidFeedbackMaximum() {
            if(this.stateMinimumMaximum)
            {
                return ""
            }
            return "Maximum value has to be greater than or equal to Minimum value."
        },
        stateMeasuringUnit() {
            if(!this.dadoBiomedico.measuringUnit)
            {
                return null
            }
            return this.dadoBiomedico.measuringUnit.length >= 3
        },
        invalidFeedbackMeasuringUnit() {
            if(this.stateMeasuringUnit)
            {
                return ""
            }
            return "Measuring Unit has to be at least 3 in length."
        },
        isFormValid() {
            return (this.stateName && this.stateMeasuringUnit && this.stateMinimumMaximum)
        }
    },
    methods: {
        onSubmit(event) {
            this.$axios.$post('api/dadosbiomedicos/', this.dadoBiomedico)
            .then(() => {
                this.$router.push("/doentes")
                alert("a")
            })
            .catch( error => {
                alert(error)
            })
        }
    }
}
</script>

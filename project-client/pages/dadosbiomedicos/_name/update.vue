<template>
    <b-container>
        <h1>Update Dado Biomédico:</h1>
        <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" :disabled="!isFormValid">
            <b-form-group
                id="inputNameGroup"
                label="Name:"
                label-for="inputName"
            >
                <b-form-input
                    id="inputName"
                    v-model="dadoBiomedico.nome"
                    disabled
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="inputDescriptionGroup"
                label="Description:"
                label-for="inputDescription"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackDescription"
                :state="stateDescription"
            >
                <b-form-input
                    id="inputDescription"
                    v-model="dadoBiomedico.descricao"
                    placeholder="Enter a description"
                    :state="stateDescription"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="inputMinimumGroup"
                label="Minimum value:"
                label-for="inputMinimum"
                :state="stateMinimumMaximum"
            >
                <b-form-input
                    id="inputMinimum"
                    v-model="dadoBiomedico.minimo"
                    type="number"
                    step="any"
                    :state="stateMinimumMaximum"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="inputMaximumGroup"
                label="Maximum value:"
                label-for="inputMaximum"
                :invalid-feedback="invalidFeedbackMinimumMaximum"
                :state="stateMinimumMaximum"
            >
                <b-form-input
                    id="inputMaximum"
                    v-model="dadoBiomedico.maximo"
                    type="number"
                    step="any"
                    :state="stateMinimumMaximum"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="inputMeasuringUnitGroup"
                label="Measuring unit:"
                label-for="inputMeasuringUnit"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackMeasuringUnit"
                :state="stateMeasuringUnit"
            >
                <b-form-input
                    id="inputMeasuringUnit"
                    v-model="dadoBiomedico.unidadeMedida"
                    placeholder="Enter the corresponding measuring unit"
                    :state="stateMeasuringUnit"
                    required
                ></b-form-input>
            </b-form-group>
            <b-form-group
                id="inputQualificadorGroup"
                label="Qualifier:"
                label-for="inputQualificador"
                :invalid-feedback="invalidFeedbackQualificador"
                :state="stateQualificador"
            >
                <b-form-input
                    id="inputQualificador"
                    v-model="qualifier"
                    placeholder="Enter a qualifier"
                    :state="stateQualificador"
                >
                </b-form-input>
            </b-form-group>

            <b-button class="mb-4" variant="success" :disabled="!this.stateQualificador" @click="addQualifier">Add Qualifier</b-button>

            <template v-if="this.dadoBiomedico.qualificadores.length == 0">
                <p class="text-danger">At least one qualifier needs to be added.</p>
            </template>
            <template>
                <b-col cols="4" class="px-0">
                    <b-list-group>
                        <b-list-group-item v-for="qualificador in dadoBiomedico.qualificadores" :key="qualificador">
                            {{qualificador}}<b-button variant="danger" class="float-right" @click="removeQualifier(qualificador)">x</b-button>
                        </b-list-group-item>
                    </b-list-group>
                </b-col>
            </template>

            <div class="text-right">
                <b-button variant="warning" type="submit" :disabled="!isFormValid">Update</b-button>
                <b-button variant="danger" type="reset">Reset</b-button>
            </div>
        </b-form>
    </b-container>
</template>

<script>

import dadosBiomedicosPostPutDelete from "../../../middleware/dadosBiomedicosPostPutDelete";

export default {
    middleware: dadosBiomedicosPostPutDelete,
    data () {
        return {
            qualifier: "",
            dadoBiomedico: {
                nome: null,
                descricao: null,
                minimo: null,
                maximo: null,
                unidadeMedida: null,
                qualificadores: []
            },
        }
    },
    created () {
        this.$axios.$get('api/dadosbiomedicos/' + this.$route.params.name).then((dadoBiomedico) => {
            this.dadoBiomedico = dadoBiomedico
        }).catch(error => {
            this.$toast.error("Could not get dado biomédico (" + this.$route.params.id + ").")
            this.$router.push("/dadosbiomedicos")
        })
    },
    computed: {
        stateDescription() {
            if(!this.dadoBiomedico.descricao) {
                return null
            }
            return this.dadoBiomedico.descricao.length >= 5
        },
        invalidFeedbackDescription() {
            if(!this.stateDescription) {
                return "Name has to be at least 5 in length."
            }
            return ""
        },
        stateMinimumMaximum() {
            if(this.dadoBiomedico.minimo == null || !this.dadoBiomedico.maximo == null) {
                return null
            }
            return this.dadoBiomedico.minimo <= this.dadoBiomedico.maximo
        },
        invalidFeedbackMinimumMaximum() {
            if(!this.stateMinimumMaximum)
            {
                return "Maximum value has to be equal to or more than the minimum value."
            }
            return ""
        },
        stateMeasuringUnit() {
            if(!this.dadoBiomedico.unidadeMedida) {
                return null
            }
            return this.dadoBiomedico.unidadeMedida.length >= 1
        },
        invalidFeedbackMeasuringUnit() {
            if(!this.stateMeasuringUnit)
            {
                return "Measuring unit has to be at least 1 in length."
            }
            return ""
        },
        stateQualificador() {
            if(!this.qualifier) {
                return null
            }
            return this.qualifier.length >= 3 && (this.dadoBiomedico.qualificadores.find(qualificador => qualificador == this.qualifier) == undefined)
        },
        invalidFeedbackQualificador() {
            if(!this.stateQualificador)
            {
                return "Qualifier has to be at least 3 in length and can not be already added to the list."
            }
            return ""
        },
        isFormValid() {
            return this.stateMinimumMaximum && this.stateDescription && this.stateMeasuringUnit
        }
    },
    methods: {
        removeQualifier(index){
            this.dadoBiomedico.qualificadores.splice(index, 1);
        },
        addQualifier() {
            this.dadoBiomedico.qualificadores.push(this.qualifier);
            this.qualifier = null;
        },
        onSubmit() {
            this.$axios.$put('api/dadosbiomedicos/' + this.$route.params.name, this.dadoBiomedico).then(() => {
                this.$toast.success("Successfully updated dado biomédico (" + this.dadoBiomedico.nome + ").")
                this.$router.push("/dadosbiomedicos")
            }).catch(error => {
                this.$toast.error("Could not update dado biomédico.<\/br>Error: '" + error.response.data + "'")
            })
        },
        onReset() {
            this.dadoBiomedico.descricao = null
            this.dadoBiomedico.minimo = null
            this.dadoBiomedico.maximo = null
            this.dadoBiomedico.unidadeMedida = null
            this.dadoBiomedico.qualificadores = []
        }
    }
}
</script>

<template>
    <b-container>
        <h1 class="mb-4 mt-4">New Observação:</h1>
        <b-form @submit.prevent="onSubmit" @reset.prevent="onReset" :disabled="!isFormValid">
            <b-form-group
                id="fieldset-doente-email"
                label="Doente Email Address:"
                label-for="inputDoenteEmail"
                valid-feedback="Done!"
                :state="stateDoenteEmail"
            >
                <b-form-select id="inputDoenteEmail" v-model="observacao.doenteEmail" :state="stateDoenteEmail">
                    <template v-for="doente in doentes" >
                        <b-form-select-option :value="doente.email">{{doente.email}}</b-form-select-option>
                    </template>
                </b-form-select>
            </b-form-group>

            <b-form-group
                id="fieldset-dado-biomedico"
                label="Dado Biomédico:"
                label-for="inputDadoBiomedico"
                valid-feedback="Done!"
                :state="stateNomeDadoBiomedico"
            >
                <b-form-select id="inputDadoBiomedico" v-model="observacao.nomeDadoBiomedico" @change="changeDadoBiomedico" :state="stateNomeDadoBiomedico">
                    <template v-for="dado in this.dadosBiomedicos" >
                        <b-form-select-option :value="dado.nome">{{dado.nome}}</b-form-select-option>
                    </template>
                </b-form-select>
            </b-form-group>

            <b-form-group
                id="fieldset-data"
                label="Date:"
                label-for="inputData"
                valid-feedback="Done!"
            >
                <b-form-datepicker disabled id="inputData" v-model="observacao.data"></b-form-datepicker>
            </b-form-group>

            <template v-if="observacao.nomeDadoBiomedico != null && dadoBiomedico != null">
                <b-form-group
                    id="fieldset-valor-quantitativo"
                    label="Quantitative Value:"
                    label-for="inputValorQuantitativo"
                    valid-feedback="Done!"
                    :invalid-feedback="invalidFeedbackQuantitativeValue"
                    :state="stateQuantitativeValue"
                >
                    <b-form-input
                        id="inputValorQuantitativo"
                        type="number"
                        :min="dadoBiomedico.minimo"
                        :max="dadoBiomedico.maximo"
                        v-model="observacao.valorQuantitativo"
                        :state="stateQuantitativeValue"
                    >
                    </b-form-input>
                </b-form-group>

                <b-form-group
                    id="fieldset-valor-qualitativo"
                    label="Qualitative Value:"
                    label-for="inputValorQualitativo"
                    valid-feedback="Done!"
                    :state="stateQualitativeValue"
                >
                    <b-form-select id="inputValorQuanlitativo" v-model="observacao.valorQualitativo" :state="stateQualitativeValue">
                        <template v-for="qualificador in this.dadoBiomedico.qualificadores" >
                            <b-form-select-option :value="qualificador">{{qualificador}}</b-form-select-option>
                        </template>
                    </b-form-select>
                </b-form-group>
            </template>

            <div class="text-right">
                <b-button variant="primary" type="submit" :disabled="!isFormValid">Create</b-button>
                <b-button variant="danger" type="reset">Reset</b-button>
            </div>
        </b-form>
    </b-container>
</template>

<script>
import observacoesPrescricoesPostPutDelete from "../../middleware/observacoesPrescricoesPostPutDelete";

export default {
    middleware: observacoesPrescricoesPostPutDelete,
    data () {
        let now = new Date()
        let today = new Date(now.getFullYear(), now.getMonth(), now.getDate())

        return {
            doentes: [],
            dadosBiomedicos:[],
            dadoBiomedico: null,
            observacao: {
                doenteEmail: null,
                profissionalDeSaudeEmail: this.$auth.user.sub,
                nomeDadoBiomedico: null,
                data: today,
                valorQuantitativo: null,
                valorQualitativo: null,
            }
        }
    },
    created () {
        this.$axios.$get('/api/dadosbiomedicos').then(dados => {
            this.dadosBiomedicos = dados
        }).catch(error => {
            this.$toast.error("Could not get available dados biomedicos.")
            this.$router.push("/observacoes")
        })

        this.$axios.$get('/api/doentes').then(doentes => {
            this.doentes = doentes
        }).catch(error => {
            this.$toast.error("Could not get list of doentes.")
            this.$router.push("/observacoes")
        })
    },
    computed:{
        stateDoenteEmail() {
            if(!this.observacao.doenteEmail)
            {
                return null
            }
            return this.observacao.doenteEmail != null
        },
        stateNomeDadoBiomedico() {
            if(!this.observacao.nomeDadoBiomedico)
            {
                return null
            }
            return this.observacao.nomeDadoBiomedico != null
        },
        stateDate() {
            if(!this.observacao.data)
            {
                return null
            }
            return this.observacao.data != null
        },
        stateQuantitativeValue() {
            if(!this.observacao.valorQuantitativo) {
                return null
            }
            return this.observacao.valorQuantitativo <= this.dadoBiomedico.maximo && this.observacao.valorQuantitativo >= this.dadoBiomedico.minimo
        },
        invalidFeedbackQuantitativeValue() {
            if(!this.stateQuantitativeValue){
                return this.dadoBiomedico.nome + "'s value has to be between " + this.dadoBiomedico.minimo + " and " + this.dadoBiomedico.maximo + "."
            }
            return ""
        },
        stateQualitativeValue() {
            if(!this.observacao.valorQualitativo)
            {
                return null
            }
            return this.observacao.valorQualitativo != null
        },
        isFormValid() {
            return this.stateDoenteEmail && this.stateNomeDadoBiomedico && this.stateDate && this.stateQualitativeValue && this.stateQuantitativeValue
        }
    },
    methods: {
        changeDadoBiomedico(){
            let index = this.dadosBiomedicos.findIndex(dadoBiomedico => dadoBiomedico.nome == this.observacao.nomeDadoBiomedico)
            this.dadoBiomedico = this.dadosBiomedicos[index]
            this.observacao.valorQuantitativo = null
            this.observacao.valorQualitativo = null
        },
        onSubmit() {
            this.$axios.$post('api/observacoes/', this.observacao).then(() => {
                this.$toast.success("New observação was created successfully!")
                this.$router.push('/observacoes')
            }).catch(error => {
                this.$toast.error("Could not create new observação.<\/br>Error: '" + error.response.data + "'")
            })
        },
        onReset() {
            this.observacao.doenteEmail = null
            this.observacao.nomeDadoBiomedico = null
            this.observacao.valorQualitativo = null
            this.observacao.valorQuantitativo = null
        }
    }
}
</script>

<template>
    <b-container>
        <h1 class="mb-4 mt-4">Update Observação {{this.$route.params.id}}:</h1>

        <template v-if="loadingCounter == 3">
            <b-form @submit.prevent="update" @reset.prevent="onReset" :disabled="!isFormValid">
                <b-form-group
                    id="fieldset-doente-email"
                    label="Doente Email Address:"
                    label-for="inputDoenteEmail"
                    valid-feedback="Done!"
                    :state="stateDoenteEmail"
                >
                    <b-form-select id="inputDoenteEmail" v-model="observacao.doenteEmail" :state="stateDoenteEmail">
                        <template v-for="doente in doentes">
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
                    <b-button variant="warning" type="submit" :disabled="!isFormValid">Update</b-button>
                    <b-button variant="danger" type="reset">Reset</b-button>
                </div>
            </b-form>
        </template>
        <template v-else>
            <Spinner/>
        </template>
    </b-container>
</template>

<script>
import observacoesPrescricoesPostPutDelete from "../../../middleware/observacoesPrescricoesPostPutDelete";
import Spinner from "../../../components/Spinner"

export default {
    middleware: observacoesPrescricoesPostPutDelete,
    components: {Spinner},
    data () {
        return {
            firstChange: true,
            loadingCounter: 0,
            doentes: null,
            dadosBiomedicos:null,
            dadoBiomedico: null,
            observacao: null,
        }
    },
    created () {
        this.$axios.$get('/api/observacoes/' + this.$route.params.id).then(observacao => {
            this.observacao = observacao
            this.loadingCounter+=1
        }).catch(error => {
            this.$toast.error("Could not get observacao (" + this.$route.params.id + ")." )
            this.$router.push("/observacoes")
        })

        this.$axios.$get('/api/dadosbiomedicos').then(dados => {
            this.dadosBiomedicos = []
            this.dadosBiomedicos.push(...dados)
            this.changeDadoBiomedico()
            this.loadingCounter+=1
        }).catch(error => {
            this.$toast.error("Could not get available dados biomedicos.")
            this.$router.push("/observacoes")
        })

        this.$axios.$get('/api/doentes').then(doentes => {
            this.doentes = []
            this.doentes.push(...doentes)
            this.loadingCounter+=1
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
            if(this.firstChange){
                this.firstChange = false
                return
            }
            this.observacao.valorQuantitativo = null
            this.observacao.valorQualitativo = null
            return
        },
        update: function () {
            this.$axios.$put('api/observacoes/' + this.$route.params.id, this.observacao).then(() => {
                this.$toast.success("Observação updated successfully!")
                this.$router.push('/observacoes')
            }).catch(error => {
                this.$toast.error("Could not update observação.<\/br>Error: '" + error.response.data + "'")
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

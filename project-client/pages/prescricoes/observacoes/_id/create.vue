<template>
    <b-container>
        <h3>New Prescrição</h3>
        <b-form @submit.prevent="create" @reset.prevent="onReset" :disabled="!isFormValid">
            <b-form-group
                id="groupSelectTipoPrescricao"
                label="Prescrião Type:"
                label-for="selectTipoPrescricao"
                valid-feedback="Done!"
                :state="stateTipoPrescricao"
            >
                <b-form-select id="selectTipoPrescricao" name="tipoPrescricao" v-model="prescricao.tipoPrescricao" :options="options" :state="stateTipoPrescricao"></b-form-select>
            </b-form-group>
            <b-form-group
                id="inputCausaGroup"
                label="Observação ID:"
                label-for="inputObservacaoID"
            >
                <b-input
                    id="inputObservacaoID"
                    name="observacaoID"
                    v-model.trim="prescricao.observacaoID"
                    disabled
                    required/>
            </b-form-group>
            <b-form-group
                id="inputComentarioGroup"
                label="Comment:"
                label-for="inputComentario"
                valid-feedback="Done!"
                :invalid-feedback="invalidFeedbackComentario"
                :state="stateComentario"
            >
                <b-form-textarea
                    id="inputComentario"
                    name="comentario"
                    placeholder="Comment regarding the prescrição."
                    v-model.trim="prescricao.comentario"
                    rows="6"
                    :state="stateComentario"
                    required
                ></b-form-textarea>
            </b-form-group>
            <b-form-group
                id="inputEmail"
                label="Doente Email Address:"
                label-for="inputEmail"
            >
                <b-input
                    id="inputEmail"
                    name="email"
                    type="email"
                    v-model="prescricao.doenteEmail"
                    disabled
                    required/>
            </b-form-group>
            <b-form-group
                id="inputGroupDataInicio"
                label="Start Date:"
                label-for="inputDataInicio"
                :state="stateDatas"
            >
                <b-form-datepicker id="inputDataInicio" :state="stateDatas" v-model="prescricao.dataInicio" class="mb-2"></b-form-datepicker>
            </b-form-group>
            <b-form-group
                id="inputGroupDataFinal"
                label="End Date:"
                label-for="inputDataFinal"
                valid-feedback="Done!"
                :state = "stateDatas"
                :invalid-feedback="invalidFeedbackDatas"
            >
                <b-form-datepicker id="inputDataFinal" :state="stateDatas" v-model="prescricao.dataFinal" class="mb-2"></b-form-datepicker>
            </b-form-group>
            <div class="text-right">
                <b-button type="submit" class="btn-success" :disabled="!isFormValid">Create</b-button>
                <b-button variant="danger" type="reset" :disabled="fieldsAreEmpty">Reset</b-button>
            </div>
        </b-form>
    </b-container>
</template>

<script>
import observacoesPrescricoesPostPutDelete from '../../../../middleware/observacoesPrescricoesPostPutDelete'

export default {
    middleware: observacoesPrescricoesPostPutDelete,
    auth: false,
    data() {
        return {
            options:
            [
                {value: "prescricaoExercicioFisico",text: "Prescrição Exercicio Fisico"},
                {value: "prescricaoMedica",text: "Prescrição Médica"},
                {value: "prescricaoNutricao",text: "Prescrição Nutrição"}
            ],
            observacao: null,
            prescricao:
            {
                comentario: null,
                doenteEmail: null,
                dataInicio: null,
                dataFinal: null,
                tipoPrescricao: null,
                observacaoID: null,
            }
        }
    },
    created(){
        this.prescricao.observacaoID=this.$route.params.id;

        this.$axios.$get('/api/observacoes/' + this.prescricao.observacaoID).then(observacao => {
            this.observacao = observacao
            this.prescricao.doenteEmail = this.observacao.doenteEmail
        }).catch(error => {
            this.$toast.error("Error getting observação to fill prescricao fields.")
            this.$router.push("/observacoes")
        })
    },
    computed: {
        stateTipoPrescricao() {
            if(!this.prescricao.tipoPrescricao)
            {
                return null
            }
            return this.prescricao.tipoPrescricao != null
        },
        stateComentario() {
            if(!this.prescricao.comentario)
            {
                return null
            }
            return this.prescricao.comentario.length >= 5
        },
        stateDatas()
        {
            if(!this.prescricao.dataInicio || !this.prescricao.dataFinal)
            {
                return null
            }
            return this.prescricao.dataInicio <= this.prescricao.dataFinal
        },
        invalidFeedbackComentario() {
            if(this.stateComentario)
            {
                return ""
            }
            return "Comentario is too short (at least 5 in length)."
        },
        invalidFeedbackDatas() {
            if(this.stateDatas)
            {
                return ""
            }
            return "Start date has to be the same or lower than the End date."
        },
        isFormValid() {
            return this.stateDatas && this.stateComentario
        },
        fieldsAreEmpty() {
            return !this.prescricao.comentario && !this.prescricao.dataInicio && !this.prescricao.dataFinal
        }
    },
    methods: {
        create() {
            this.prescricao.observacaoID=this.$route.params.id

            this.$axios.$post('/api/prescricoes', this.prescricao).then(() => {
                this.$toast.success("Prescricao was created with success!")
                this.$router.push('/prescricoes')
            })
        },
        onReset() {
            this.prescricao.comentario = null
            this.prescricao.dataFinal = null
            this.prescricao.dataInicio = null
            this.prescricao.tipoPrescricao = null
        }
    }
}
</script>

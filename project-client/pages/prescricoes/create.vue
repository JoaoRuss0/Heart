<template>
    <b-container>
        <h3>Passar Prescrição</h3>
        <b-form @submit.prevent="create" @reset.prevent="onReset" :disabled="!isFormValid">
            <label>Seleciona o Tipo de Prescrição:</label>
            <select v-model="prescricao.tipoPrescricao" id="tipoPrescricao" name="tipoPrescricao">
                <option value="prescricaoExercicioFisico">Prescrição Exercicio Fisico</option>
                <option value="prescricaoMedica">Prescrição Médica</option>
                <option value="prescricaoNutricao">Prescrição Nutrição</option>
            </select>

            <b-form-group
                id="inputCausaGroup"
                label="Causa"
                label-for="inputCausa"
                :invalid-feedback="invalidFeedbackCausa"
                :state="stateCausa"
            >
                <b-input
                    id="inputCausa"
                    ref="causa"
                    name="causa"
                    placeholder="A causa da prescrição"
                    v-model.trim="prescricao.causa"
                    :state="stateCausa"
                    required/>
            </b-form-group>
            <b-form-group
                id="inputEmail"
                label="Email do Doente:"
                label-for="inputEmail"
                :invalid-feedback="invalidFeedbackEmail"
                :state="stateEmail"
            >
                <b-input
                    id="inputEmail"
                    name="email"
                    ref="email"
                    type="email"
                    placeholder="example:bomdiaxau@gmail.com"
                    v-model="prescricao.doenteEmail"
                    :state="stateEmail"
                    required/>
            </b-form-group>

            <div>
                <label >Data Inicio</label>
                <b-form-datepicker id="dataInicial" v-model="prescricao.dataInicio" class="mb-2"></b-form-datepicker>

            </div>
            <div>
                <label >Data Final</label>
                <b-form-datepicker id="dataFinal" v-model="prescricao.dataFinal" class="mb-2"></b-form-datepicker>

            </div>

            <b-button type="submit" class="btn-success">Create</b-button>
        </b-form>
    </b-container>
</template>

<script>
export default {
    auth: false,
    data() {
        return {
         prescricao:
        {
            causa: null,
            doenteEmail: null,
            dataInicio: null,
            dataFinal: null,
            tipoPrescricao: "prescricaoExercicioFisico"
        }
        }
    },
    computed: {
        stateEmail() {
            if(!this.email)
            {
                return null
            }
            return this.$refs.email.checkValidity();
        },
        stateCausa() {
            if(!this.causa)
            {
                return null
            }
            return this.causa.length >= 5
        },
        invalidFeedbackEmail() {
            if(this.stateEmail)
            {
                return ""
            }
            return "Not a valid email address."
        },
        invalidFeedbackCausa() {
            if(this.stateCausa)
            {
                return ""
            }
            return "Causa is too short (at least 5 in length)."
        },
        isFormValid() {
            return (this.stateEmail && this.causa)
        }
    },
    methods: {
        create () {
            this.$axios
                .$post('/api/prescricoes', this.prescricao)
                .then(() => {
                    this.$router.push('/prescricoes')
                })
        }
    }
}
</script>

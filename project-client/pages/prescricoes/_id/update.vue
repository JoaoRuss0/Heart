<template>
    <div>
        <b-container>
            <h1 class="mb-4 mt-4">
                Update Prescricao com o ID nº{{ prescricao['id'] }}
            </h1>
            <form @submit.prevent="create">
                <b-row class="mb-3">
                    <b-col><strong>Causa:</strong></b-col>
                    <b-col><input v-model="prescricao['causa']" type="text"  placeholder="${ prescricao.causa }"/></b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Email do Doente:</strong></b-col>
                    <b-col><input v-model="prescricao['doenteEmail']" type="text"  /></b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Data Inicio:</strong></b-col>
                    <b-col>
                        <b-form-datepicker id="dataInicial" v-model="prescricao['dataInicio']" class="mb-2"></b-form-datepicker></b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Data Final:</strong></b-col>
                    <b-col>
                        <b-form-datepicker id="dataFinal" v-model="prescricao['dataFinal']" class="mb-2"></b-form-datepicker></b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Tipo Prescrição:</strong></b-col>
                    <b-col>
                    <select v-model="prescricao['tipoPrescricao']" id="tipoPrescricao" name="tipoPrescricao">
                        <option value="prescricaoExercicioFisico">Prescrição Exercicio Fisico</option>
                        <option value="prescricaoMedica">Prescrição Médica</option>
                        <option value="prescricaoNutricao">Prescrição Nutrição</option>
                    </select>
                    </b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col>
                        <button @click.prevent="update" class="btn btn-light">UPDATE</button>
                    </b-col>
                </b-row>
            </form>
        </b-container>
    </div>
</template>

<script>
import observacoesPrescricoesPostPutDelete from '../../../middleware/observacoesPrescricoesPostPutDelete'

export default {
    middleware: observacoesPrescricoesPostPutDelete,
    data () {
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
    created () {
        this.$axios.$get('api/prescricoes/' + this.$route.params.id).then((prescricao) => {
            this.prescricao = prescricao
        })
    },
    methods: {
        update: function () {
            this.$axios.$put('api/prescricoes/' + this.$route.params.id, {
                "causa": this.prescricao.causa,
                "doenteEmail": this.prescricao.doenteEmail,
                "dataInicio": this.prescricao.dataInicio,
                "dataFinal":this.prescricao.dataFinal,
                "tipoPrescricao": this.prescricao.tipoPrescricao


            }).then(() =>
                this.$router.push('/prescricoes')
            )
        }
    }
}
</script>


<template>
    <div>
        <b-container fluid="sm">
            <div v-if="prescricao">
                <h1 class="mb-4 mt-4">
                    Prescricão com o ID nº{{ prescricao['id'] }}
                </h1>
                <b-list-group>
                    <b-list-group-item><strong>Causa: </strong>{{ prescricao['causa'] }} </b-list-group-item>
                    <b-list-group-item variant="secondary"><strong>Email do Doente: </strong>{{ prescricao['doenteEmail'] }} </b-list-group-item>
                    <b-list-group-item><strong>Data Inicio: </strong>{{ prescricao['dataInicio'] }}</b-list-group-item>
                    <b-list-group-item variant="secondary"><strong>Data Final: </strong>{{ prescricao['dataFinal'] }}</b-list-group-item>
                    <b-list-group-item><strong>Tipo Prescrição: </strong>{{ prescricao['tipoPrescricao'] }}</b-list-group-item>
                </b-list-group>


                
            </div>
        </b-container>
    </div>
</template>

<script>
export default {
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
}
</script>

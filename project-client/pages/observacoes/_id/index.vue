<template>
        <b-container fluid="sm">
            <div v-if="observacao">
                <h1 class="mb-4 mt-4">
                    Observação #{{ observacao['id'] }}
                </h1>
                <b-row class="mb-3">
                    <b-col><strong>Email do Doente:</strong></b-col> <b-col>{{ observacao['doenteEmail'] }}</b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Email do Profissional de Saúde:</strong></b-col> <b-col>{{ observacao['profissionalDeSaudeEmail'] }}</b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Dado Biomédico:</strong></b-col> <b-col>{{ observacao['nomeDadoBiomedico'] }}</b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Data:</strong></b-col> <b-col>{{ observacao['data'] }}</b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Valor Quantitativo:</strong></b-col> <b-col>{{ observacao['valorQuantitativo'] }}</b-col>
                </b-row>
                <b-row class="mb-3">
                    <b-col><strong>Valor Qualitativo:</strong></b-col> <b-col>{{ observacao['valorQualitativo'] }}</b-col>
                </b-row>

                <b-row class="mb-3">
                    <b-col>
                        <nuxt-link to="/observacoes">
                            <button class="btn btn-warning">Return</button>
                        </nuxt-link>
                    </b-col>
                </b-row>
            </div>
        </b-container>
</template>

<script>
export default {
    data () {
        return {
            observacao: null,
        }
    },
    created () {
        this.$axios.$get('api/observacoes/' + this.$route.params.id).then((observacao) => {
            this.observacao = observacao
        })
    },
}
</script>

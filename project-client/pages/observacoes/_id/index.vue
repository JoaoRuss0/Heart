<template>
    <b-container>
        <h1>Observação details:</h1>
        <template v-if="observacao != null">
            <b-form-group id="fieldset-id" label="ID:">
                <b-form-input :disabled="true" :value="observacao.id"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-doenteEmail" label="Doente's Email Address:">
                <b-form-input :disabled="true" :value="observacao.doenteEmail"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-profissionalDeSaudeEmail" label="Profissional De Saude's Email Address:">
                <b-form-input :disabled="true" :value="observacao.profissionalDeSaudeEmail"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-nomeDadoBiomedico" label="Dado Biomedico's Name:">
                <b-form-input :disabled="true" :value="observacao.nomeDadoBiomedico"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-data" label="Date:">
                <b-form-input :disabled="true" :value="observacao.data"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-valorQuantitativo" label="Quantitative Value:">
                <b-form-input :disabled="true" :value="observacao.valorQuantitativo"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-valorQualitativo" label="Qualitative Value:">
                <b-form-input :disabled="true" :value="observacao.valorQualitativo"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-prescricaoID" label="Prescrição ID:">
                <b-form-input :disabled="true" :value="((observacao.prescricaoID == 0) ? 'No prescrição is associated with this observação.' : observacao.prescricaoID)"></b-form-input>
            </b-form-group>
        </template>
        <template v-else>
            <Spinner/>
        </template>
    </b-container>
</template>

<script>
import Spinner from "../../../components/Spinner";

export default {
    components: {Spinner},
    data () {
        return {
            observacao: null,
        }
    },
    created () {
        this.$axios.$get('api/observacoes/' + this.$route.params.id).then((observacao) => {
            this.observacao = observacao
        }).catch(error => {
            this.$toast.error("Could not get observação (" + this.$route.params.id + ").")
            this.$router.push("/observacoes")
        })
    },
}
</script>

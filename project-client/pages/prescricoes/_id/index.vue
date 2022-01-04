<template>
    <b-container>
        <h1>Prescrição details:</h1>
        <template v-if="prescricao != null">
            <b-form-group id="fieldset-id" label="ID:">
                <b-form-input :disabled="true" :value="prescricao.id"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-comentario" label="Comment:">
                <b-form-input :disabled="true" :value="prescricao.comentario"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-doenteEmail" label="Doente's Email Address:">
                <b-form-input :disabled="true" :value="prescricao.doenteEmail"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-dataInicio" label="Start Date:">
                <b-form-input :disabled="true" :value="prescricao.dataInicio"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-dataFinal" label="Final Date:">
                <b-form-input :disabled="true" :value="prescricao.dataFinal"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-tipoPrescricao" label="Prescription Type:">
                <b-form-input :disabled="true" :value="prescricao.tipoPrescricao"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-profissionalDeSaudeEmail" label="Profissional de Saúde's Email Address:">
                <b-form-input :disabled="true" :value="prescricao.profissionalDeSaudeEmail"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-observacaoID" label="Observação ID:">
                <b-form-input :disabled="true" :value="prescricao.observacaoID"></b-form-input>
            </b-form-group>
        </template>
        <template v-else>
            <Spinner/>
        </template>
    </b-container>
</template>

<script>
export default {
    data () {
        return {
            prescricao: null
        }
    },
    created () {
        this.$axios.$get('api/prescricoes/' + this.$route.params.id).then((prescricao) => {
            this.prescricao = prescricao
        }).catch(error => {
            this.$toast.error("Could not get prescrição (" + this.$route.params.id + ").")
            this.$router.push("/prescricoes")
        })
    },
}
</script>

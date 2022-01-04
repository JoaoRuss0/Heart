<template>
    <b-container>
        <h1>Dado Biomédico details:</h1>
        <template v-if="dadoBiomedico != null">
            <b-form-group id="fieldset-nome" label="Name:">
                <b-form-input :disabled="true" :value="dadoBiomedico.nome"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-descricao" label="Description:">
                <b-form-input :disabled="true" :value="dadoBiomedico.descricao"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-minimo" label="Minimum value:">
                <b-form-input :disabled="true" :value="dadoBiomedico.minimo"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-maximo" label="Maximum value:">
                <b-form-input :disabled="true" :value="dadoBiomedico.maximo"></b-form-input>
            </b-form-group>
            <b-form-group id="fieldset-unidadeMedida" label="Measuring unit:">
                <b-form-input :disabled="true" :value="dadoBiomedico.unidadeMedida"></b-form-input>
            </b-form-group>
            <b-col cols="4" class="px-0">
                <b-list-group>
                    <b-list-group-item v-for="qualificador in dadoBiomedico.qualificadores" :key="qualificador">
                        {{qualificador}}
                    </b-list-group-item>
                </b-list-group>
            </b-col>
        </template>
        <template v-else>
            <Spinner/>
        </template>
    </b-container>
</template>

<script>
import dadosBiomedicosIndex from "../../../middleware/dadosBiomedicosIndex";
import Spinner from "../../../components/Spinner";

export default {
    components: {Spinner},
    middleware: dadosBiomedicosIndex,
    data () {
        return {
            dadoBiomedico: null,
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
}
</script>

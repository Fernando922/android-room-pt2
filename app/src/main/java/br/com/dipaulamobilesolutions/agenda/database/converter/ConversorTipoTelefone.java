package br.com.dipaulamobilesolutions.agenda.database.converter;

import androidx.room.TypeConverter;

import br.com.dipaulamobilesolutions.agenda.model.activity.TipoTelefone;

public class ConversorTipoTelefone {

    @TypeConverter
    public String paraString(TipoTelefone tipo) {
        return tipo.name();
    }

    @TypeConverter
    public TipoTelefone paraTipoTelefone(String valor) {
        if (valor != null) {
            return TipoTelefone.valueOf(valor);
        }
        return TipoTelefone.FIXO;
    }
}

package nta.engine.function.builtin;

import nta.catalog.Column;
import nta.catalog.proto.CatalogProtos;
import nta.datum.Datum;
import nta.datum.DatumFactory;
import nta.datum.StringDatum;
import nta.engine.function.AggFunction;
import nta.engine.function.FunctionContext;
import nta.storage.Tuple;
import nta.storage.VTuple;

/**
 * @author Hyunsik Choi
 */
public class NewMinString extends AggFunction<Datum> {

  public NewMinString() {
    super(new Column[] {
        new Column("val", CatalogProtos.DataType.STRING)
    });
  }

  @Override
  public FunctionContext newContext() {
    return new MinContext();
  }

  @Override
  public void eval(FunctionContext ctx, Tuple params) {
    MinContext minCtx = (MinContext) ctx;
    if (minCtx.min == null) {
      minCtx.min = params.get(0).asChars();
    } else if (params.get(0).asChars().compareTo(minCtx.min) < 0) {
      minCtx.min = params.get(0).asChars();
    }
  }

  @Override
  public Tuple getPartialResult(FunctionContext ctx) {
    Tuple part = new VTuple(1);
    part.put(0, DatumFactory.createString(((MinContext)ctx).min));
    return part;
  }

  @Override
  public StringDatum terminate(FunctionContext ctx) {
    return DatumFactory.createString(((MinContext)ctx).min);
  }

  private class MinContext implements FunctionContext {
    String min;
  }
}
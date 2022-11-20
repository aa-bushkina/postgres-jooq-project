/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.InvoicesPositions;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InvoicesPositionsRecord extends UpdatableRecordImpl<InvoicesPositionsRecord> implements Record3<Integer, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.invoices_positions.id</code>.
     */
    public InvoicesPositionsRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.invoices_positions.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.invoices_positions.invoice_id</code>.
     */
    public InvoicesPositionsRecord setInvoiceId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.invoices_positions.invoice_id</code>.
     */
    public Integer getInvoiceId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.invoices_positions.position_id</code>.
     */
    public InvoicesPositionsRecord setPositionId(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.invoices_positions.position_id</code>.
     */
    public Integer getPositionId() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Integer, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, Integer, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return InvoicesPositions.INVOICES_POSITIONS.ID;
    }

    @Override
    public Field<Integer> field2() {
        return InvoicesPositions.INVOICES_POSITIONS.INVOICE_ID;
    }

    @Override
    public Field<Integer> field3() {
        return InvoicesPositions.INVOICES_POSITIONS.POSITION_ID;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getInvoiceId();
    }

    @Override
    public Integer component3() {
        return getPositionId();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getInvoiceId();
    }

    @Override
    public Integer value3() {
        return getPositionId();
    }

    @Override
    public InvoicesPositionsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public InvoicesPositionsRecord value2(Integer value) {
        setInvoiceId(value);
        return this;
    }

    @Override
    public InvoicesPositionsRecord value3(Integer value) {
        setPositionId(value);
        return this;
    }

    @Override
    public InvoicesPositionsRecord values(Integer value1, Integer value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached InvoicesPositionsRecord
     */
    public InvoicesPositionsRecord() {
        super(InvoicesPositions.INVOICES_POSITIONS);
    }

    /**
     * Create a detached, initialised InvoicesPositionsRecord
     */
    public InvoicesPositionsRecord(Integer id, Integer invoiceId, Integer positionId) {
        super(InvoicesPositions.INVOICES_POSITIONS);

        setId(id);
        setInvoiceId(invoiceId);
        setPositionId(positionId);
    }

    /**
     * Create a detached, initialised InvoicesPositionsRecord
     */
    public InvoicesPositionsRecord(generated.tables.pojos.InvoicesPositions value) {
        super(InvoicesPositions.INVOICES_POSITIONS);

        if (value != null) {
            setId(value.getId());
            setInvoiceId(value.getInvoiceId());
            setPositionId(value.getPositionId());
        }
    }
}

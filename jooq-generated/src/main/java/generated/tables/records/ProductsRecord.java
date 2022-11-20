/*
 * This file is generated by jOOQ.
 */
package generated.tables.records;


import generated.tables.Products;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProductsRecord extends UpdatableRecordImpl<ProductsRecord> implements Record3<Integer, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.products.id</code>.
     */
    public ProductsRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.products.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.products.internal_code</code>.
     */
    public ProductsRecord setInternalCode(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.products.internal_code</code>.
     */
    public String getInternalCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.products.name</code>.
     */
    public ProductsRecord setName(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.products.name</code>.
     */
    public String getName() {
        return (String) get(2);
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
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Products.PRODUCTS.ID;
    }

    @Override
    public Field<String> field2() {
        return Products.PRODUCTS.INTERNAL_CODE;
    }

    @Override
    public Field<String> field3() {
        return Products.PRODUCTS.NAME;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getInternalCode();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getInternalCode();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public ProductsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public ProductsRecord value2(String value) {
        setInternalCode(value);
        return this;
    }

    @Override
    public ProductsRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public ProductsRecord values(Integer value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProductsRecord
     */
    public ProductsRecord() {
        super(Products.PRODUCTS);
    }

    /**
     * Create a detached, initialised ProductsRecord
     */
    public ProductsRecord(Integer id, String internalCode, String name) {
        super(Products.PRODUCTS);

        setId(id);
        setInternalCode(internalCode);
        setName(name);
    }

    /**
     * Create a detached, initialised ProductsRecord
     */
    public ProductsRecord(generated.tables.pojos.Products value) {
        super(Products.PRODUCTS);

        if (value != null) {
            setId(value.getId());
            setInternalCode(value.getInternalCode());
            setName(value.getName());
        }
    }
}

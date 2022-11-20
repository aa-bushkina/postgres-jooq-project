/*
 * This file is generated by jOOQ.
 */
package generated;


import generated.tables.FlywaySchemaHistory;
import generated.tables.Invoices;
import generated.tables.InvoicesPositions;
import generated.tables.Organizations;
import generated.tables.Positions;
import generated.tables.Products;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.invoices</code>.
     */
    public final Invoices INVOICES = Invoices.INVOICES;

    /**
     * The table <code>public.invoices_positions</code>.
     */
    public final InvoicesPositions INVOICES_POSITIONS = InvoicesPositions.INVOICES_POSITIONS;

    /**
     * The table <code>public.organizations</code>.
     */
    public final Organizations ORGANIZATIONS = Organizations.ORGANIZATIONS;

    /**
     * The table <code>public.positions</code>.
     */
    public final Positions POSITIONS = Positions.POSITIONS;

    /**
     * The table <code>public.products</code>.
     */
    public final Products PRODUCTS = Products.PRODUCTS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            Invoices.INVOICES,
            InvoicesPositions.INVOICES_POSITIONS,
            Organizations.ORGANIZATIONS,
            Positions.POSITIONS,
            Products.PRODUCTS
        );
    }
}
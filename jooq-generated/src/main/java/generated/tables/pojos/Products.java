/*
 * This file is generated by jOOQ.
 */
package generated.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer id;
    private final String internalCode;
    private final String name;

    public Products(Products value) {
        this.id = value.id;
        this.internalCode = value.internalCode;
        this.name = value.name;
    }

    public Products(
        Integer id,
        String internalCode,
        String name
    ) {
        this.id = id;
        this.internalCode = internalCode;
        this.name = name;
    }

    /**
     * Getter for <code>public.products.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Getter for <code>public.products.internal_code</code>.
     */
    public String getInternalCode() {
        return this.internalCode;
    }

    /**
     * Getter for <code>public.products.name</code>.
     */
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Products other = (Products) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.internalCode == null) {
            if (other.internalCode != null)
                return false;
        }
        else if (!this.internalCode.equals(other.internalCode))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.internalCode == null) ? 0 : this.internalCode.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Products (");

        sb.append(id);
        sb.append(", ").append(internalCode);
        sb.append(", ").append(name);

        sb.append(")");
        return sb.toString();
    }
}

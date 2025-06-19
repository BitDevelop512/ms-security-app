package co.invima.security.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericRequestDTO implements Serializable {
    public Object request;

    public static GenericRequestDTOBuilder builder() {
        return new GenericRequestDTOBuilder();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof GenericRequestDTO)) {
            return false;
        } else {
            GenericRequestDTO other = (GenericRequestDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$request = this.getRequest();
                Object other$request = other.getRequest();
                if (this$request == null) {
                    if (other$request != null) {
                        return false;
                    }
                } else if (!this$request.equals(other$request)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof GenericRequestDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $request = this.getRequest();
        result = result * 59 + ($request == null ? 43 : $request.hashCode());
        return result;
    }

    public String toString() {
        return "GenericRequestDTO(request=" + this.getRequest() + ")";
    }

    public Object getRequest() {
        return this.request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public GenericRequestDTO() {
    }

    public GenericRequestDTO(Object request) {
        this.request = request;
    }

    public static class GenericRequestDTOBuilder {
        private Object request;

        GenericRequestDTOBuilder() {
        }

        public GenericRequestDTOBuilder request(Object request) {
            this.request = request;
            return this;
        }

        public GenericRequestDTO build() {
            return new GenericRequestDTO(this.request);
        }

        public String toString() {
            return "GenericRequestDTO.GenericRequestDTOBuilder(request=" + this.request + ")";
        }
    }
}

/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.ejb.client;

import java.io.Serializable;
import java.util.Objects;

import org.wildfly.common.Assert;

/**
 * An identifier for an EJB located within a container.  This identifier only names the EJB; it does not specify
 * a view, which must be done using the {@link EJBLocator} family of types.
 * <p>
 * EJB identifiers are suitable for use as hash keys.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class EJBIdentifier implements Serializable {
    private static final long serialVersionUID = 7065644117552778408L;

    private final String appName;
    private final String moduleName;
    private final String beanName;
    private final String distinctName;
    private transient int hashCode;

    /**
     * Construct a new instance.
     *
     * @param appName the application name (must not be {@code null})
     * @param moduleName the module name (must not be {@code null} or empty)
     * @param beanName the bean name (must not be {@code null} or empty)
     * @param distinctName the distinct name (must not be {@code null})
     */
    public EJBIdentifier(final String appName, final String moduleName, final String beanName, final String distinctName) {
        Assert.checkNotNullParam("appName", appName);
        Assert.checkNotNullParam("moduleName", moduleName);
        Assert.checkNotEmptyParam("moduleName", moduleName);
        Assert.checkNotNullParam("beanName", beanName);
        Assert.checkNotEmptyParam("beanName", beanName);
        Assert.checkNotNullParam("distinctName", distinctName);
        this.appName = appName;
        this.moduleName = moduleName;
        this.beanName = beanName;
        this.distinctName = distinctName;
    }

    /**
     * Get the application name, which may be empty.
     *
     * @return the application name (not {@code null})
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Get the module name.
     *
     * @return the module name (not {@code null})
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Get the bean name.
     *
     * @return the bean name (not {@code null})
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * Get the distinct name.
     *
     * @return the distinct name (not {@code null})
     */
    public String getDistinctName() {
        return distinctName;
    }

    /**
     * Determine if this EJB identifier is equal to the given object.
     *
     * @param other the object to test
     * @return {@code true} if the object is equal to this one, {@code false} otherwise
     */
    public boolean equals(final Object other) {
        return other instanceof EJBIdentifier && equals((EJBIdentifier) other);
    }

    /**
     * Determine if this EJB identifier is equal to the given object.
     *
     * @param other the object to test
     * @return {@code true} if the object is equal to this one, {@code false} otherwise
     */
    public boolean equals(final EJBIdentifier other) {
        return other != null && (
            other == this ||
                other.hashCode() == hashCode() &&
                Objects.equals(appName, other.appName) &&
                Objects.equals(moduleName, other.moduleName) &&
                Objects.equals(beanName, other.beanName) &&
                Objects.equals(distinctName, other.distinctName)
        );
    }

    /**
     * Get the hash code of this identifier.
     *
     * @return the hash code of this identifier
     */
    public int hashCode() {
        int hashCode = this.hashCode;
        if (hashCode != 0) {
            return hashCode;
        }
        hashCode = Objects.hashCode(appName) + 13 * (Objects.hashCode(moduleName) + 13 * (Objects.hashCode(beanName) + 13 * Objects.hashCode(distinctName)));
        return this.hashCode = hashCode == 0 ? 1 : hashCode;
    }

    /**
     * Get the EJB identifier as a human-readable string.
     *
     * @return the EJB identifier as a human-readable string (not {@code null})
     */
    public String toString() {
        final String distinctName = getDistinctName();
        if (distinctName == null || distinctName.isEmpty()) {
            return String.format("%s/%s/%s", getAppName(), getModuleName(), getBeanName());
        } else {
            return String.format("%s/%s/%s/%s", getAppName(), getModuleName(), getBeanName(), distinctName);
        }
    }
}

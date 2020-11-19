/*
Some components may require an additional routine to be executed upon being added to a GameObject.
For example, RotationComponent requires its rotateTransform object to be added to the
transforms list of the corresponding GameObject's MeshComponent.

Such a component should be able to supply a callback handle to the EntityManager to facilitate
the demand.
ComponentCallback is a functional interface for the same.
 */

public interface ComponentCallback {
    void initialize(EntityManager entityManager, GameObject gameObject);
}

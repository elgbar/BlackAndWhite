package no.kh498.bnw.hexagon;

import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.api.contract.HexagonDataStorage;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;
import org.codetome.hexameter.core.internal.GridData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Default implementation of the {@link HexagonPubImpl} interface.
 */
@SuppressWarnings("NonJREEmulationClassesInClientCode")
public final class HexagonPubImpl<T extends SatelliteData> implements org.codetome.hexameter.core.api.Hexagon<T> {

    private final CubeCoordinate coordinate;
    private final transient List<Double> vertices;
    private final transient List<Point> points;
    private final transient Rectangle externalBoundingBox;
    private final transient Rectangle internalBoundingBox;
    private final transient GridData sharedData;
    private final transient HexagonDataStorage<T> hexagonDataStorage;

    /**
     * Creates a new {@link HexagonPubImpl} object from shared data and a coordinate.
     *
     * @param gridData
     *     grid data
     * @param coordinate
     *     coordinate
     * @param hexagonDataStorage
     *     data map
     **/
    public HexagonPubImpl(final GridData gridData, final CubeCoordinate coordinate,
                          final HexagonDataStorage<T> hexagonDataStorage) {
        this.sharedData = gridData;
        this.coordinate = coordinate;
        this.hexagonDataStorage = hexagonDataStorage;

        this.points = calculatePoints();
        final int x1 = (int) this.points.get(3).getCoordinateX();
        final int y1 = (int) this.points.get(2).getCoordinateY();
        final int x2 = (int) this.points.get(0).getCoordinateX();
        final int y2 = (int) this.points.get(5).getCoordinateY();

        this.externalBoundingBox = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        this.internalBoundingBox = new Rectangle((int) (getCenterX() - (1.25 * this.sharedData.getRadius() / 2)),
                                                 (int) (getCenterY() - (1.25 * this.sharedData.getRadius() / 2)),
                                                 (int) (1.25f * this.sharedData.getRadius()),
                                                 (int) (1.25f * this.sharedData.getRadius()));

        this.vertices = new ArrayList<>();
        for (final Point point : this.points) {
            this.vertices.add(point.getCoordinateX());
            this.vertices.add(point.getCoordinateY());
        }
    }

    private List<Point> calculatePoints() {
        final List<Point> points = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            final double angle = 2 * Math.PI / 6 * (i + this.sharedData.getOrientation().getCoordinateOffset());
            final double x = getCenterX() + this.sharedData.getRadius() * cos(angle);
            final double y = getCenterY() + this.sharedData.getRadius() * sin(angle);
            points.add(Point.fromPosition(x, y));
        }
        return points;
    }

    @Override
    public String getId() {
        return this.coordinate.toAxialKey();
    }

    @Override
    public List<Point> getPoints() {
        return this.points;
    }

    @Override
    public List<Double> getVertices() {
        return this.vertices;
    }

    @Override
    public Rectangle getExternalBoundingBox() {
        return this.externalBoundingBox;
    }

    @Override
    public Rectangle getInternalBoundingBox() {
        return this.internalBoundingBox;
    }

    @Override
    public CubeCoordinate getCubeCoordinate() {
        return this.coordinate;
    }

    @Override
    public int getGridX() {
        return this.coordinate.getGridX();
    }

    @Override
    public int getGridY() {
        return this.coordinate.getGridY();
    }

    @Override
    public int getGridZ() {
        return this.coordinate.getGridZ();
    }

    @Override
    public double getCenterX() {
        if (HexagonOrientation.FLAT_TOP.equals(this.sharedData.getOrientation())) {
            return this.coordinate.getGridX() * this.sharedData.getHexagonWidth() + this.sharedData.getRadius();
        }
        else {
            return this.coordinate.getGridX() * this.sharedData.getHexagonWidth() +
                   this.coordinate.getGridZ() * this.sharedData.getHexagonWidth() / 2 +
                   this.sharedData.getHexagonWidth() / 2;
        }
    }

    @Override
    public double getCenterY() {
        if (HexagonOrientation.FLAT_TOP.equals(this.sharedData.getOrientation())) {
            return this.coordinate.getGridZ() * this.sharedData.getHexagonHeight() +
                   this.coordinate.getGridX() * this.sharedData.getHexagonHeight() / 2 +
                   this.sharedData.getHexagonHeight() / 2;
        }
        else {
            return this.coordinate.getGridZ() * this.sharedData.getHexagonHeight() + this.sharedData.getRadius();
        }
    }

    @Override
    public Optional<T> getSatelliteData() {
        return this.hexagonDataStorage.getSatelliteDataBy(getCubeCoordinate());
    }

    @Override
    public void setSatelliteData(final T satelliteData) {
        this.hexagonDataStorage.addCoordinate(getCubeCoordinate(), satelliteData);
    }

    @Override
    public void clearSatelliteData() {
        this.hexagonDataStorage.clearDataFor(getCubeCoordinate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.coordinate);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final HexagonPubImpl hexagonPubImpl = (HexagonPubImpl) object;
        return Objects.equals(this.coordinate, hexagonPubImpl.coordinate);
    }
}

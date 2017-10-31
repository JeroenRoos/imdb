package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.model.ActorView;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.DirectorView;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.GenreView;
import nl.inholland.layers.model.Serie;
import nl.inholland.layers.model.SerieView;

/**
 *
 * @author Jeroen
 */
public class SeriePresenter extends BasePresenter
{
    private GenrePresenter genrePresenter = new GenrePresenter();
    private ActorPresenter actorPresenter = new ActorPresenter();
    private DirectorPresenter directorPresenter = new DirectorPresenter();
    
    public SerieView present(Serie serie)
    {
        SerieView serieView = initSerieView(serie);
        return serieView;
    }

    public List<SerieView> present(List<Serie> lstSeries)
    {
        List<SerieView> view = new ArrayList<SerieView>();
        for (Serie serie : lstSeries)
        {
            SerieView serieView = initSerieView(serie);
            view.add(serieView);
        }
        return view;
    }

    private SerieView initSerieView(Serie serie)
    {
        SerieView serieView = new SerieView();

        serieView.setId(serie.getId());
        serieView.setTitle(serie.getTitle());
        serieView.setSummary(serie.getSummary());
        serieView.setYear(serie.getYear());

        serieView.setGenre(genrePresenter.present(serie.getGenre()));
        serieView.setDirector(directorPresenter.present(serie.getDirectors()));
        serieView.setActors(actorPresenter.present(serie.getActors()));
        
        return serieView;
    }
}

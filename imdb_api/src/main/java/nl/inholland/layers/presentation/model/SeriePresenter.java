package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.model.ActorView;
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

        serieView.setTitle(serie.getTitle());
        serieView.setSummary(serie.getSummary());
        serieView.setYear(serie.getYear());

        List<GenreView> lstGenreViews = new ArrayList<>();
        for (Genre genre : serie.getGenre())
        {
            GenreView genreView = new GenreView();
            genreView.setName(genre.getName());
            lstGenreViews.add(genreView);
        }
        serieView.setGenre(lstGenreViews);

        List<ActorView> lstActorViews = new ArrayList<>();
        for (Actor actor : serie.getActors())
        {
            ActorView actorView = new ActorView();
            actorView.setFirstName(actor.getFirstName());
            actorView.setLastName(actor.getLastName());
            actorView.setAge(actor.getAge());
            lstActorViews.add(actorView);
        }
        serieView.setActors(lstActorViews);

        DirectorView directorView = new DirectorView();
        directorView.setAge(serie.getDirector().getAge());
        directorView.setFirstName(serie.getDirector().getFirstName());
        directorView.setLastName(serie.getDirector().getLastName());
        serieView.setDirector(directorView);

        return serieView;
    }
}

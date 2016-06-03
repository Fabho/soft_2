package com.proyecto.petdroid;

import java.io.File;
import java.util.Locale;
import java.util.Vector;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.proyecto.petdroid.adaptadores.AdaptadorDrawer;
import com.proyecto.petdroid.almacen.AlmacenMascotas;
import com.proyecto.petdroid.almacen.AlmacenSQLite;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	//Declaro la interfaz para el almacenamiento de datos
	public static AlmacenMascotas almacen; 
	
	//AsyncTask
	private ServicioPrincipal tarea;
	
	private static String PREFERENCIAS = "preferencias";
	SharedPreferences preferencias;
	SharedPreferences.Editor editor;
	
	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	
	private Vector<String> opcionesMenu = new Vector<String>();
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
  
    private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Inicio la base de datos
		almacen = new AlmacenSQLite(this);
		
		//Lanzo el AsynTask
		tarea = new ServicioPrincipal();
		tarea.execute(this);
		
		//Recupero las preferencias
		preferencias = this.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
		editor = preferencias.edit();
		
		// Configuro la action bar
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setHomeButtonEnabled(true);

		//Creo el adaptador que devolverá cada fragmentepara cada una de las secciones principales
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			
			getActionBar().addTab(getActionBar().newTab().setText(
					mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
		
		for(String s : getResources().getStringArray(R.array.drawer_array)){
		
			opcionesMenu.add(s);
		}
		 
	     drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	     drawerList = (ListView) findViewById(R.id.left_drawer);
	     
	     drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	     
	     drawerList.setAdapter(new AdaptadorDrawer(opcionesMenu, this));
	     
	     drawerList.setOnItemClickListener(new OnItemClickListener() {
	    	 
	    	 Intent i;
	    	 
	         @Override
	         public void onItemClick(AdapterView parent, View view,
	                 int position, long id) {
	        	 
	             switch (position) {
	                 case 0:
	                	 i = new Intent(getBaseContext(), Mascotas.class);
	                	 startActivity(i);
	                     break;
	                 case 1:
	                	 i = new Intent(getBaseContext(), NuevaMascota.class);
	                	 startActivity(i);
						 break;
	                 case 2:
	                	 i = new Intent(getBaseContext() , Propietarios.class);
						 i.putExtra("alta", false);
						 startActivity(i);
	                     break;
	                 case 3:
	                	 i = new Intent(getBaseContext(), NuevoPropietario.class);
						i.putExtra("altaNueva", true);
						startActivity(i);
	                     break;
	                 case 4:
	                	 pintarAyuda();
	                	 break;
	             }
	  
	             drawerList.setItemChecked(position, true);
	  
	             drawerLayout.closeDrawer(drawerList);
	             
	         }
	     });
	     
	     getActionBar().setDisplayHomeAsUpEnabled(true);
	     getActionBar().setHomeButtonEnabled(true);

	     drawerToggle = new ActionBarDrawerToggle(this,
	    	        drawerLayout,
	    	        R.drawable.ic_navigation_drawer,
	    	        R.string.drawer_open,
	    	        R.string.drawer_close) {
	    	 
	    	        public void onDrawerClosed(View view) {
	    	        	getActionBar().setTitle(R.string.app_name);
	    	            ActivityCompat.invalidateOptionsMenu(MainActivity.this);
	    	        }
	    	 
	    	        public void onDrawerOpened(View drawerView) {
	    	        	getActionBar().setTitle("Navegación");
	    	            ActivityCompat.invalidateOptionsMenu(MainActivity.this);
	    	        }
	    	    };
	    	 
	    	    drawerLayout.setDrawerListener(drawerToggle);
	}

	
	@Override
	protected void onResume() {
		
		super.onResume();
		if(preferencias.getBoolean("primera_ejecucion", true)){
			editor.putBoolean("primera_ejecucion", false);
			editor.commit();
			drawerLayout.postDelayed(new Runnable() {
		        @Override
		        public void run() {
		        	drawerLayout.openDrawer(Gravity.LEFT);
		        }
		    }, 500);
		}
		
	}


	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
       
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (drawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }
		
		switch (item.getItemId()) {
	        
	        case R.id.menu_info:
	        
	        	pintarAyuda();
	        	
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	    public void setTitle(CharSequence title) {
	        mTitle = title;
	        getActionBar().setTitle(mTitle);
	    }
	
	private void pintarAyuda(){
		
		int flag;
    	
		if(preferencias.getBoolean("ayuda", false)){
			editor.putBoolean("ayuda", false);
			flag = View.VISIBLE;
		}
		else{
			editor.putBoolean("ayuda", true);
			flag = View.INVISIBLE;
		}
		editor.commit();
		TextView txtAyuda1Mascotas = (TextView)mViewPager.findViewById(R.id.txtAyuda1Mascotas);
    	TextView txtAyuda2Mascotas = (TextView)mViewPager.findViewById(R.id.txtAyuda2Mascotas);
    	TextView txtAyuda3Mascotas = (TextView)mViewPager.findViewById(R.id.txtAyuda3MAscotas);
    	TextView txtAyuda1Propietarios = (TextView)mViewPager.findViewById(R.id.txtAyuda1Propietarios);
    	TextView txtAyuda2Propietarios = (TextView)mViewPager.findViewById(R.id.txtAyuda2Propietario);
    	TextView txtAyuda3Propietarios = (TextView)mViewPager.findViewById(R.id.txtAyuda3Propietarios);
    	txtAyuda1Mascotas.setVisibility(flag);
    	txtAyuda2Mascotas.setVisibility(flag);
    	txtAyuda3Mascotas.setVisibility(flag);
    	txtAyuda1Propietarios.setVisibility(flag);
    	txtAyuda2Propietarios.setVisibility(flag);
    	txtAyuda3Propietarios.setVisibility(flag);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	 
	    boolean menuAbierto = drawerLayout.isDrawerOpen(drawerList);
	 
	    if(menuAbierto)
	        menu.findItem(R.id.menu_info).setVisible(false);
	    else
	        menu.findItem(R.id.menu_info).setVisible(true);
	 
	    return super.onPrepareOptionsMenu(menu);
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			Fragment fragment;
			if(position == 0){
				fragment = new FragmentPortadaMascotas();
			}
			else{
				fragment = new FragmentPortadaPropietarios();
			}
			return fragment;
			
		}

		@Override
		public int getCount() {
			
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}
	
	private class ServicioPrincipal extends AsyncTask<Activity, Void, Boolean>{
		
		@Override
		protected Boolean doInBackground(Activity... params) {
			
			File appFolder = Environment.getExternalStorageDirectory();
			File directorio = new File(appFolder.getAbsolutePath()+"/PetDroid/Fotos");
	    	if (!directorio.exists()){
	    	    directorio.mkdirs();
	    	}
	    	preferencias = params[0].getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
			editor = preferencias.edit();
	    	editor.putBoolean("ayuda", true);
	    	editor.commit();
	    	
	    	return true;
		}
		
	}

	}

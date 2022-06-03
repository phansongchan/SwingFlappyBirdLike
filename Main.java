interface Window
{
    int WND_WIDTH  = 400;
    int WND_HEIGHT = 600;
    int DELAY = 30;
}

class Gameplay extends javax.swing.JPanel implements java.awt.event.ActionListener, java.awt.event.KeyListener
{

	private int score = 0;
	private int px = 50, py = Window.WND_HEIGHT / 2, pr = 25;
	private int dy = 0;

	// TUPES VAR //
	private int dtube = 3;
	private int tube1X = 200, tube2X = 400, tube3X = 800;
	private int tubeWidth = 30;
	private int tube1Height = (int)( Math.random() * Window.WND_HEIGHT / 2 );
	private int tube2Height = (int)( Math.random() * Window.WND_HEIGHT / 2 );
	private int tube3Height = (int)( Math.random() * Window.WND_HEIGHT / 2 );
	private int d2tube = 150;		// Distance between 2 tubes
	
	
	private boolean isRunning;
	private javax.swing.Timer gameTimer = new javax.swing.Timer( Window.DELAY, this );
	
    public Gameplay()
    {
        addKeyListener( this );
        setFocusable( true );
        setFocusTraversalKeysEnabled( false );

		setBackground( java.awt.Color.BLACK );

		gameTimer.start();
		isRunning = true;
    }

    public void paintComponent( java.awt.Graphics g )
    {
        super.paintComponent( g );
        draw( g );
    }

    public void draw( java.awt.Graphics g )
    {

        java.awt.Graphics2D g2d = (java.awt.Graphics2D)g;

        g2d.setRenderingHint(
                             java.awt.RenderingHints.KEY_ANTIALIASING,
                             java.awt.RenderingHints.VALUE_ANTIALIAS_ON );

        // You can also enable antialiasing for text:

        g2d.setRenderingHint(
                             java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                             java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON );

		// Player
		g.setColor( java.awt.Color.WHITE );
		g.fillOval( px, py, pr, pr );

		// TUPES
		g.fillRect( tube1X, 0, tubeWidth, tube1Height );
		g.fillRect( tube1X, tube1Height + d2tube, tubeWidth, Window.WND_HEIGHT - d2tube - tube1Height );
		
		g.fillRect( tube2X, 0, tubeWidth, tube2Height );
		g.fillRect( tube2X, tube2Height + d2tube, tubeWidth, Window.WND_HEIGHT - d2tube - tube2Height );

		g.fillRect( tube3X, 0, tubeWidth, tube3Height );
		g.fillRect( tube3X, tube3Height + d2tube, tubeWidth, Window.WND_HEIGHT - d2tube - tube3Height );

		g.setColor( java.awt.Color.YELLOW );
		g.setFont( new java.awt.Font( "serif", java.awt.Font.PLAIN, 20 ) );
		g.drawString( "SCORE " + score, 0, 20 );
	}

    @Override public void actionPerformed( java.awt.event.ActionEvent e )
    {		
		if ( isRunning ) {
			score++;

			if ( score % 200 == 0 ) dtube += 1;
			
			dy += 1;
			py += dy;

			if ( py < 0 || py > Window.WND_HEIGHT ) isRunning = false;

			// MOVING TUBES //
			tube1X -= dtube;
			tube2X -= dtube;
			tube3X -= dtube;

			if ( tube1X < -Window.WND_WIDTH ) {
				tube1X = Window.WND_WIDTH;
				tube1Height = (int)( Math.random() * Window.WND_HEIGHT / 2 );
			}

			if ( tube2X < -Window.WND_WIDTH ) {
				tube2X = Window.WND_WIDTH;
				tube2Height = (int)( Math.random() * Window.WND_HEIGHT / 2 );
			}

			if ( tube3X < -Window.WND_WIDTH ) {
				tube3X = Window.WND_WIDTH;
				tube3Height = (int)( Math.random() * Window.WND_HEIGHT / 2 );
			}


			// COLLISION //
			java.awt.Rectangle playerRect = new java.awt.Rectangle( px, py, pr, pr );

			if ( playerRect.intersects( new java.awt.Rectangle( tube1X, 0, tubeWidth, tube1Height ) ) ) {
				isRunning = false;
			}

			if ( playerRect.intersects( new java.awt.Rectangle( tube1X, tube1Height + d2tube , tubeWidth, Window.WND_HEIGHT - d2tube - tube1Height ) ) ) {
				isRunning = false;
			}

			if ( playerRect.intersects( new java.awt.Rectangle( tube2X, 0, tubeWidth, tube2Height ) ) ) {
				isRunning = false;
			}

			if ( playerRect.intersects( new java.awt.Rectangle( tube2X, tube2Height + d2tube , tubeWidth, Window.WND_HEIGHT - d2tube - tube2Height ) ) ) {
				isRunning = false;
			}

			if ( playerRect.intersects( new java.awt.Rectangle( tube3X, 0, tubeWidth, tube3Height ) ) ) {
				isRunning = false;
			}

			if ( playerRect.intersects( new java.awt.Rectangle( tube3X, tube3Height + d2tube , tubeWidth, Window.WND_HEIGHT - d2tube - tube3Height ) ) ) {
				isRunning = false;
			}

		}

		if ( !isRunning ) {
			gameTimer.stop();
			System.out.println( "GAME OVER" );
		}
        repaint();
    }

    @Override public void keyPressed( java.awt.event.KeyEvent e )
    {
		if ( e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE ) {
			dy = -10;
		}
    }

    @Override public void keyTyped( java.awt.event.KeyEvent e )
    {    }

    @Override public void keyReleased( java.awt.event.KeyEvent e )
    {    }

    
}

class Main extends javax.swing.JFrame
{

    public Main()
    {
        pack();
        add( new Gameplay() );
        setTitle( "Flappy!" );
        setSize( Window.WND_WIDTH, Window.WND_HEIGHT );
        setLocationRelativeTo( null );
        setVisible( true );
        setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE );
    }
    
    public static void main( String[] args )
    {
        new Main();
    }
}

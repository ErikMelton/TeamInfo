/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class gx extends xe
/*     */ {
/*     */   protected String a;
/*     */   private int b;
/* 182 */   private static final String c = age.a;
/*     */ 
/*  22 */   private boolean dragging = false;
/*     */   private int diffu;
/*     */   private int diffv;
/*     */   private int k;
/*     */   private int l;
/*  28 */   int size = 40;
/*     */ 
/*     */   public gx()
/*     */   {
/*  32 */     this.a = "";
/*  33 */     this.b = 0;
/*     */   }
/*     */ 
/*     */   public void a()
/*     */   {
/*  38 */     Keyboard.enableRepeatEvents(true);
/*     */ 
/*  40 */     if (!Util.hunger) this.size -= 10;
/*  41 */     if (!Util.armour) this.size -= 10;
/*  42 */     if (!Util.coords) this.size -= 10;
/*     */ 
/*  44 */     if (this.size < 20) this.size = 20;
/*     */   }
/*     */ 
/*     */   public void d()
/*     */   {
/*  49 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   public void s_()
/*     */   {
/*  54 */     this.b += 1;
/*     */   }
/*     */ 
/*     */   protected void a(char c, int i)
/*     */   {
/*  59 */     if (i == 1)
/*     */     {
/*  61 */       this.l.a(null);
/*  62 */       return;
/*     */     }
/*  64 */     if (i == 28)
/*     */     {
/*  66 */       String s = this.a.trim();
/*  67 */       if (s.length() > 0)
/*     */       {
/*  69 */         String s1 = this.a.trim();
/*  70 */         if (!this.l.c(s1))
/*     */         {
/*  72 */           this.l.h.a(s1);
/*     */         }
/*     */       }
/*  75 */       this.l.a(null);
/*  76 */       return;
/*     */     }
/*  78 */     if ((i == 14) && (this.a.length() > 0))
/*     */     {
/*  80 */       this.a = this.a.substring(0, this.a.length() - 1);
/*     */     }
/*  82 */     if ((c.indexOf(c) >= 0) && (this.a.length() < 100))
/*     */     {
/*  84 */       this.a = new StringBuilder().append(this.a).append(c).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void a(int i, int j, float f)
/*     */   {
/*  90 */     a(2, this.n - 14, this.m - 2, this.n - 2, -2147483648);
/*  91 */     b(this.q, new StringBuilder().append("> ").append(this.a).append(this.b / 6 % 2 != 0 ? "" : "_").toString(), 4, this.n - 12, 14737632);
/*     */ 
/*  93 */     int x = Mouse.getX() / 2;
/*  94 */     int y = (this.l.e - Mouse.getY()) / 2;
/*     */ 
/*  97 */     for (String player : TeamInfo.players.keySet())
/*     */     {
/*  99 */       if ((Mouse.isButtonDown(0)) && (!((Boolean)TeamInfo.dragable.get(player)).booleanValue()) && (x >= ((Integer)TeamInfo.toplefti.get(player)).intValue()) && (x <= ((Integer)TeamInfo.toplefti.get(player)).intValue() + 108.0F * Util.scale) && (y >= ((Integer)TeamInfo.topleftj.get(player)).intValue()) && (y <= ((Integer)TeamInfo.topleftj.get(player)).intValue() + this.size * Util.scale))
/*     */       {
/* 101 */         if (Keyboard.isKeyDown(29))
/*     */         {
/* 103 */           TeamInfo.showgui.put(player, Boolean.valueOf(!((Boolean)TeamInfo.showgui.get(player)).booleanValue()));
/*     */         }
/*     */ 
/* 106 */         int p = 0;
/* 107 */         for (String players : TeamInfo.players.keySet())
/*     */         {
/* 109 */           if (((Boolean)TeamInfo.dragable.get(players)).booleanValue()) p++;
/*     */         }
/*     */ 
/* 112 */         if (p < 1)
/*     */         {
/* 114 */           this.diffu = (x - ((Integer)TeamInfo.toplefti.get(player)).intValue());
/* 115 */           this.diffv = (y - ((Integer)TeamInfo.topleftj.get(player)).intValue());
/* 116 */           TeamInfo.dragable.put(player, Boolean.valueOf(true));
/*     */         }
/*     */       }
/* 119 */       else if ((Mouse.isButtonDown(0)) && (((Boolean)TeamInfo.dragable.get(player)).booleanValue()))
/*     */       {
/* 121 */         TeamInfo.toplefti.put(player, Integer.valueOf(x - this.diffu));
/* 122 */         TeamInfo.topleftj.put(player, Integer.valueOf(y - this.diffv));
/*     */       }
/*     */       else
/*     */       {
/* 127 */         TeamInfo.dragable.put(player, Boolean.valueOf(false));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 132 */     for (String player : TeamInfo.players.keySet())
/*     */     {
/* 134 */       GL11.glTranslatef(((Integer)TeamInfo.toplefti.get(player)).intValue(), ((Integer)TeamInfo.topleftj.get(player)).intValue(), 0.0F);
/*     */ 
/* 136 */       int colour = 553582592;
/* 137 */       if (((Boolean)TeamInfo.showgui.get(player)).booleanValue())
/*     */       {
/* 139 */         colour = 536936192;
/*     */       }
/*     */       else
/*     */       {
/* 144 */         a(this.q, player, (int)(108.0F * Util.scale) / 2, (int)(20.0F * Util.scale) / 2, -16711936);
/*     */       }
/*     */ 
/* 147 */       a(0, 0, (int)(108.0F * Util.scale), (int)(this.size * Util.scale), colour);
/* 148 */       GL11.glTranslatef(-((Integer)TeamInfo.toplefti.get(player)).intValue(), -((Integer)TeamInfo.topleftj.get(player)).intValue(), 0.0F);
/*     */     }
/*     */ 
/* 154 */     super.a(i, j, f);
/*     */   }
/*     */ 
/*     */   protected void a(int i, int j, int k)
/*     */   {
/* 159 */     super.a(i, j, k);
/* 160 */     if (k != 0)
/*     */     {
/* 162 */       return;
/*     */     }
/* 164 */     if (this.l.w.a == null)
/*     */     {
/* 166 */       return;
/*     */     }
/* 168 */     if ((this.a.length() > 0) && (!this.a.endsWith(" ")))
/*     */     {
/* 170 */       this.a = new StringBuilder().append(this.a).append(" ").toString();
/*     */     }
/* 172 */     this.a = new StringBuilder().append(this.a).append(this.l.w.a).toString();
/* 173 */     byte byte0 = 100;
/* 174 */     if (this.a.length() > byte0)
/*     */     {
/* 176 */       this.a = this.a.substring(0, byte0);
/*     */     }
/*     */   }
/*     */ }
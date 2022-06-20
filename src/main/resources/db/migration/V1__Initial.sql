CREATE TABLE [PAYMENTS].[BATCH](
    [batch_id] [nvarchar](50) NOT NULL,
    [batch_status] [nvarchar](20) NOT NULL,
    [created_on] [datetime2](7) NOT NULL,
    [last_updated_on] [datetime2](7) NULL,
    CONSTRAINT [PK_BATCH] PRIMARY KEY CLUSTERED
      ([batch_id] ASC)
)
GO
CREATE TABLE [PAYMENTS].[PAYMENT](
    [payment_id] [nvarchar](50) NOT NULL,
    [batch_id] [nvarchar](50) NOT NULL,
    [payment_status] [nvarchar](20) NOT NULL,
    [payment_type] [nvarchar](20) NOT NULL,
    [payment_instruction] [nvarchar](max) NOT NULL,
    [created_on] [datetime2](7) NOT NULL,
    [last_updated_on] [datetime2](7) NULL,
    CONSTRAINT [PK_PAYMENT] PRIMARY KEY CLUSTERED
        ([payment_id] ASC)
)
GO
ALTER TABLE [PAYMENTS].[PAYMENT]  WITH CHECK ADD CONSTRAINT [FK_PAYMENT_BATCH] FOREIGN KEY([batch_id])
    REFERENCES [PAYMENTS].[BATCH] ([batch_id])
GO
ALTER TABLE [PAYMENTS].[PAYMENT] CHECK CONSTRAINT [FK_PAYMENT_BATCH]
GO
CREATE PROCEDURE [PAYMENTS].[createBatch](@batchId NVARCHAR(50), @paymentsJson NVARCHAR(MAX), @message nvarchar(1000) OUTPUT) as
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION

            INSERT INTO BATCH(batch_Id, batch_status,created_on)
            VALUES (@batchId, 'PENDING',GETDATE())

            INSERT INTO PAYMENT(payment_id,batch_id,payment_status,payment_instruction, payment_type, created_on)
            SELECT paymentId,@batchId, 'PENDING', paymentInstruction, paymentType,GETDATE()
            FROM OPENJSON(@paymentsJson)
                          WITH (
                              paymentId NVARCHAR(50) 'strict $.paymentId',
                              paymentInstruction NVARCHAR(MAX) 'strict $.paymentInstruction' AS JSON,
                              paymentType NVARCHAR(20) 'strict $.paymentType'
                              )

            SET @message = '{"rows":' + CAST(@@ROWCOUNT as NVARCHAR(10)) + '}'

        commit transaction
    end try
    BEGIN CATCH
        DECLARE @ErrorMessage varchar(MAX) = ERROR_MESSAGE(),
            @Severity int = 16,
            @State smallint = ERROR_STATE()

        RAISERROR(@ErrorMessage, @Severity, @State)

        ROLLBACK TRANSACTION

        SET @message = '{"rows":0, "error":"' + @ErrorMessage + '"}'
    END CATCH
END
GO